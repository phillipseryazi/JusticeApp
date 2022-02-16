package com.mudhut.software.justiceapp.domain.repositories.posts

import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mudhut.software.justiceapp.BuildConfig
import com.mudhut.software.justiceapp.data.models.CreatePostResponse
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.utils.Resource
import com.mudhut.software.justiceapp.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) : IPostRepository {
    private val tag = "PostRepository"
    private var downloadUrl = MutableStateFlow("")
    private var downloadUrls = mutableListOf<String>()


    private suspend fun uploadToCloudinary(filePath: String) {
        coroutineScope {
            MediaManager
                .get()
                .upload(Uri.parse(filePath))
                .option("folder", "JusticeApp/")
                .unsigned(BuildConfig.CLOUDINARY_UPLOAD_PRESET)
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String?) {}

                    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                        Log.d(tag, "Media Upload Progress: $bytes of $totalBytes")
                    }

                    override fun onSuccess(
                        requestId: String?,
                        resultData: MutableMap<Any?, Any?>?
                    ) {
                        downloadUrl.value = resultData?.get("url").toString()
                    }

                    override fun onError(requestId: String?, error: ErrorInfo?) {
                        Log.e(tag, "Media Upload Error: ${error?.description}")
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {}

                }).dispatch()
        }
    }

    private suspend fun savePostToFirestore(post: Post, downloadUrls: List<String>) {
        val postMap = mutableMapOf(
            "caption" to post.caption,
            "created_at" to post.created_at,
            "upvote_count" to post.upvote_count,
            "comment_count" to post.comment_count,
            "author" to firebaseAuth.currentUser?.uid,
            "media" to downloadUrls
        )

        firebaseAuth.uid?.let {
            firestore
                .collection("posts")
                .document()
                .set(postMap)
                .await()
        }
    }

    override fun createPost(post: Post) = flow {
        emit(Resource.Loading())

        post.media.forEach {
            uploadToCloudinary(it)
        }

        downloadUrl.collect {
            if (it.trim().isNotEmpty() && it.trim().isNotBlank()) {
                downloadUrls.add(it.replace("http", "https"))
            }

            if (downloadUrls.size == post.media.size) {
                savePostToFirestore(post, downloadUrls)

                emit(Resource.Success(data = CreatePostResponse(Response.SUCCESS)))
            }
        }
    }.catch {
        emit(
            Resource.Error(
                data = CreatePostResponse(Response.FAILED),
                message = it.localizedMessage ?: "Unknown error"
            )
        )
    }.flowOn(Dispatchers.IO)


    override fun getPosts(): Flow<Resource<List<Post>>> = flow<Resource<List<Post>>> {
        emit(Resource.Loading())

        val posts = firestore.collection("posts").get().await()

        emit(Resource.Success(data = posts.toObjects(Post::class.java)))
    }.catch {
        emit(
            Resource.Error(
                data = null,
                message = it.localizedMessage ?: "Unknown error"
            )
        )
    }.flowOn(Dispatchers.IO)
}

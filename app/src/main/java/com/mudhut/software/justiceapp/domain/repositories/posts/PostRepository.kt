package com.mudhut.software.justiceapp.domain.repositories.posts

import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.mudhut.software.justiceapp.BuildConfig
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.utils.Resource
import com.mudhut.software.justiceapp.utils.Response
import com.mudhut.software.justiceapp.utils.ResponseType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val database: FirebaseDatabase
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
            "author" to auth.currentUser?.uid,
            "media" to downloadUrls,
            "key" to ""
        )

        auth.uid?.let {
            database.reference
                .child("posts")
                .push()
                .setValue(postMap)
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

                emit(Resource.Success(data = Response(ResponseType.SUCCESS)))
            }
        }
    }.catch {
        emit(
            Resource.Error(
                data = Response(ResponseType.FAILED),
                message = it.localizedMessage ?: "Unknown error"
            )
        )
    }.flowOn(Dispatchers.IO)


    override fun getPosts(): Flow<Resource<List<Post?>>> = flow {
        emit(Resource.Loading())

        val dataSnapshot = database.reference.child("posts").get().await()

        val posts = dataSnapshot.children.map {
            it.key?.let { key -> it.getValue(Post::class.java)?.copy(key = key) }
        }

        emit(Resource.Success(data = posts))
    }.catch {
        emit(
            Resource.Error(
                data = null,
                message = it.localizedMessage ?: "Unknown error"
            )
        )
    }.flowOn(Dispatchers.IO)

    private suspend fun incrementUpVoteCount(postId: String) {
        val dbRef = database.reference.child("posts")

        val currentCount = dbRef.child("$postId/upvote_count").get().await()

        val newCount = currentCount.value as Int + 1

        val updateMap = mapOf(
            "$postId/upvote_count" to newCount
        )

        dbRef.updateChildren(updateMap).await()
    }

    override fun upVotePost(postId: String): Flow<Resource<Response>> = flow {
        emit(Resource.Loading())

        database.reference
            .child("likes")
            .child(postId)
            .setValue(mapOf("${auth.currentUser?.uid}" to "${auth.currentUser?.uid}"))
            .await()

        incrementUpVoteCount(postId)

        emit(Resource.Success(Response(ResponseType.SUCCESS)))
    }.catch {
        emit(
            Resource.Error(
                data = null,
                message = it.localizedMessage ?: "Unknown error"
            )
        )
    }.flowOn(Dispatchers.IO)
}

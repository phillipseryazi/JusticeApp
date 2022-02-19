package com.mudhut.software.justiceapp.domain.repositories.posts

import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mudhut.software.justiceapp.BuildConfig
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.utils.Resource
import com.mudhut.software.justiceapp.utils.Response
import com.mudhut.software.justiceapp.utils.ResponseType
import com.mudhut.software.justiceapp.utils.UNKNOWN_ERROR_MESSAGE
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
                message = it.localizedMessage ?: UNKNOWN_ERROR_MESSAGE
            )
        )
    }.flowOn(Dispatchers.IO)

    private suspend fun checkIfPostWasUpVotedByUser(postId: String): Boolean {
        val dataSnapshot = database.reference
            .child("upvotes/$postId/${auth.currentUser?.uid}")
            .get()
            .await()

        return dataSnapshot.exists()
    }


    override fun getPosts(): Flow<Resource<List<Post?>>> = flow {
        emit(Resource.Loading())

        val dataSnapshot = database.reference.child("posts").get().await()

        val posts = dataSnapshot.children.map { snapshot ->
            snapshot.getValue(Post::class.java)?.apply {
                key = snapshot.key.toString()
                isUpvoted = checkIfPostWasUpVotedByUser(snapshot.key.toString())
            }
        }

        emit(Resource.Success(data = posts))
    }.catch {
        emit(
            Resource.Error(
                data = null,
                message = it.localizedMessage ?: UNKNOWN_ERROR_MESSAGE
            )
        )
    }.flowOn(Dispatchers.IO)

    private suspend fun incrementUpVoteCount(postId: String) {
        database.reference.child("posts/$postId/upvote_count")
            .setValue(ServerValue.increment(1))
            .await()
    }

    override fun upVotePost(postId: String): Flow<Resource<Response>> = flow {
        emit(Resource.Loading())
        val dbRef = database.reference.child("upvotes/$postId")

        val alreadyVoted = dbRef.child("${auth.currentUser?.uid}").get().await()

        if (!alreadyVoted.exists()) {
            dbRef.setValue(mapOf("${auth.currentUser?.uid}" to "${auth.currentUser?.uid}"))
                .await()

            incrementUpVoteCount(postId)
        }

        emit(Resource.Success(Response(ResponseType.SUCCESS)))
    }.catch {
        emit(
            Resource.Error(
                data = null,
                message = it.localizedMessage ?: UNKNOWN_ERROR_MESSAGE
            )
        )
    }.flowOn(Dispatchers.IO)


    private suspend fun decrementUpVoteCount(postId: String) {
        database.reference.child("posts/$postId/upvote_count")
            .setValue(ServerValue.increment(-1))
            .await()
    }

    override fun unVotePost(postId: String): Flow<Resource<Response>> = flow {
        emit(Resource.Loading())

        val dbRef = database.reference.child("upvotes/$postId")

        dbRef.child("${auth.currentUser?.uid}").removeValue()

        decrementUpVoteCount(postId)

        emit(Resource.Success(Response(ResponseType.SUCCESS)))
    }.catch {
        emit(
            Resource.Error(
                data = null,
                message = it.localizedMessage ?: UNKNOWN_ERROR_MESSAGE
            )
        )
    }.flowOn(Dispatchers.IO)

}

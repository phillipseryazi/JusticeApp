package com.mudhut.software.justiceapp.domain.repositories.posts

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mudhut.software.justiceapp.data.models.CreatePostResponse
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.utils.Resource
import com.mudhut.software.justiceapp.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) : IPostRepository {
    private val downloadUrls = mutableListOf<String>()

    private suspend fun uploadMedia(uri: Uri, userId: String): Uri {
        return withContext(Dispatchers.IO) {
            val reference = storage
                .getReference("media/$userId")
                .child(UUID.randomUUID().toString())

            reference.putFile(uri).await()

            reference.downloadUrl.await()
        }
    }

    override fun createPost(post: Post) = flow {
        emit(Resource.Loading())

        downloadUrls.clear()

        post.media.forEach {
            val url = firebaseAuth.currentUser?.uid?.let { uid ->
                uploadMedia(it, uid)
            }

            if (url != null) {
                downloadUrls.add(url.toString())
            }
        }

        val postMap = mutableMapOf(
            "caption" to post.caption,
            "created_at" to post.created_at,
            "upvote_count" to post.upvote_count,
            "comment_count" to post.comment_count,
            "author" to firebaseAuth.currentUser?.uid,
            "media" to downloadUrls
        )

        firebaseAuth.uid?.let {
            firestore.collection("posts").document().set(postMap).await()
        }

        emit(Resource.Success(data = CreatePostResponse(Response.SUCCESS)))
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
        emit(Resource.Success(data = listOf()))
    }.catch {
        emit(
            Resource.Error(
                data = null,
                message = it.localizedMessage ?: "Unknown error"
            )
        )
    }.flowOn(Dispatchers.IO)
}

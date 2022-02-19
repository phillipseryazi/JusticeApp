package com.mudhut.software.justiceapp.domain.repositories.posts

import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.utils.Resource
import com.mudhut.software.justiceapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface IPostRepository {
    fun createPost(post: Post): Flow<Resource<Response>>
    fun getPosts(): Flow<Resource<List<Post?>>>
    fun upVotePost(postId: String): Flow<Resource<Response>>
    fun unVotePost(postId: String): Flow<Resource<Response>>
}

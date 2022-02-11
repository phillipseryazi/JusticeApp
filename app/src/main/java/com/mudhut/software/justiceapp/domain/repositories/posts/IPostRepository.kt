package com.mudhut.software.justiceapp.domain.repositories.posts

import com.mudhut.software.justiceapp.data.models.CreatePostResponse
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IPostRepository {
    fun createPost(post: Post): Flow<Resource<CreatePostResponse>>
    fun getPosts(): Flow<Resource<List<Post>>>
}

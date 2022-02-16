package com.mudhut.software.justiceapp.domain.usecases.dashboard

import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.domain.repositories.posts.IPostRepository
import com.mudhut.software.justiceapp.utils.Resource
import com.mudhut.software.justiceapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(private val repository: IPostRepository) {
    operator fun invoke(post: Post): Flow<Resource<Response>> = flow {
        repository.createPost(post).collect { emit(it) }
    }
}

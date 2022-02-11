package com.mudhut.software.justiceapp.domain.usecases.dashboard

import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.domain.repositories.posts.IPostRepository
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: IPostRepository) {
    operator fun invoke(): Flow<Resource<List<Post>>> = flow {
        repository.getPosts().collect { emit(it) }
    }
}

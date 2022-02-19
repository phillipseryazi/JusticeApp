package com.mudhut.software.justiceapp.domain.usecases.posts

import com.mudhut.software.justiceapp.domain.repositories.posts.IPostRepository
import com.mudhut.software.justiceapp.utils.Resource
import com.mudhut.software.justiceapp.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UnVotePostUseCase @Inject constructor(private val repository: IPostRepository) {
    operator fun invoke(postId: String): Flow<Resource<Response>> = flow {
        repository.unVotePost(postId).collect { emit(it) }
    }
}

package com.mudhut.software.justiceapp.domain.usecases.auth

import com.mudhut.software.justiceapp.domain.repositories.auth.IAuthenticationRepository
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmailPasswordRegistrationUseCase @Inject constructor(
    private val repository: IAuthenticationRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow {
        repository.emailPasswordRegistration(email, password).collect {
            emit(it)
        }
    }
}

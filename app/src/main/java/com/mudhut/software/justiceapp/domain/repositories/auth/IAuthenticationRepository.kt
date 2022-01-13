package com.mudhut.software.justiceapp.domain.repositories.auth

import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthenticationRepository {
    fun emailPasswordRegistration(email: String, password: String): Flow<Resource<Boolean>>
    fun emailPasswordLogin(email: String, password: String): Flow<Resource<Boolean>>
    fun googleLogin()
    fun anonymousLogin()
    fun logout()
}

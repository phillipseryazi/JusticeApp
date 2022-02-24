package com.mudhut.software.justiceapp.domain.repositories.auth

import com.mudhut.software.justiceapp.data.models.Profile
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthenticationRepository {
    fun emailPasswordRegistration(profile: Profile): Flow<Resource<Profile>>
    fun emailPasswordLogin(email: String, password: String): Flow<Resource<Profile>>
    fun googleLogin()
    fun anonymousLogin()
    fun logout()
}

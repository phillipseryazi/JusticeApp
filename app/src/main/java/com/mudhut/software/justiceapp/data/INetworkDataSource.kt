package com.mudhut.software.justiceapp.data

import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface INetworkDataSource {
    fun emailPasswordRegistration(email: String, password: String): Flow<Resource<Boolean>>
    fun emailPasswordLogin(email: String, password: String): Flow<Resource<Boolean>>
    fun googleLogin()
    fun anonymousLogin()
    fun createUserProfile()
    fun logout()
}

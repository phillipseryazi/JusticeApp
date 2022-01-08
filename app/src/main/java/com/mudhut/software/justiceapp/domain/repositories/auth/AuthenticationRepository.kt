package com.mudhut.software.justiceapp.domain.repositories.auth

import com.mudhut.software.justiceapp.data.NetworkDataSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : IAuthenticationRepository {

    override fun emailPasswordRegistration(email: String, password: String) = flow {
        networkDataSource.emailPasswordRegistration(email, password).collect {
            emit(it)
        }
    }

    override fun emailPasswordLogin(email: String, password: String) = flow {
        networkDataSource.emailPasswordLogin(email, password).collect {
            emit(it)
        }
    }

    override fun googleLogin() {
        TODO("Not yet implemented")
    }

    override fun anonymousLogin() {
        TODO("Not yet implemented")
    }
}

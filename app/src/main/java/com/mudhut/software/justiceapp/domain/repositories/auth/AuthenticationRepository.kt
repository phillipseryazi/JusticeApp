package com.mudhut.software.justiceapp.domain.repositories.auth

import com.google.firebase.auth.FirebaseAuth
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : IAuthenticationRepository {
    override fun emailPasswordRegistration(email: String, password: String) = flow {
        emit(Resource.Loading())
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        emit(Resource.Success(data = true))
    }.catch {
        emit(Resource.Error(data = false, message = it.localizedMessage ?: "unexpected error"))
    }.flowOn(Dispatchers.IO)

    override fun emailPasswordLogin(email: String, password: String) = flow {
        emit(Resource.Loading())
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(Resource.Success(data = true))
    }.catch {
        emit(Resource.Error(data = false, message = it.localizedMessage ?: "unexpected error"))
    }.flowOn(Dispatchers.IO)

    override fun googleLogin() {
        TODO("Not yet implemented")
    }

    override fun anonymousLogin() {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}

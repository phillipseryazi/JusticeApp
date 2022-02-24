package com.mudhut.software.justiceapp.domain.repositories.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mudhut.software.justiceapp.data.models.Profile
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : IAuthenticationRepository {
    override fun emailPasswordRegistration(profile: Profile) = flow {
        emit(Resource.Loading())

        val registration = auth
            .createUserWithEmailAndPassword(profile.email, profile.password)
            .await()

        if (registration.user != null) {
            auth.currentUser?.uid?.let { uid ->
                val userMap = mutableMapOf(
                    "username" to profile.username,
                    "uid" to uid,
                    "email" to profile.email,
                    "contact" to profile.contact,
                    "userType" to profile.userType,
                    "avatar" to profile.avatar,
                    "isVerified" to profile.isVerified
                )

                firestore.collection("profiles").document(uid).set(userMap).await()

                val userProfile = firestore
                    .collection("profiles")
                    .document(uid)
                    .get()
                    .await()
                    .toObject(Profile::class.java)

                emit(Resource.Success(data = userProfile!!))
            }
        }
    }.catch {
        emit(Resource.Error(data = null, message = it.localizedMessage ?: "unexpected error"))
    }.flowOn(Dispatchers.IO)

    override fun emailPasswordLogin(email: String, password: String) = flow {
        emit(Resource.Loading())

        val login = auth.signInWithEmailAndPassword(email, password).await()

        if (login.user != null) {
            auth.currentUser?.uid?.let { uid ->
                val profile = firestore
                    .collection("profiles")
                    .document(uid)
                    .get()
                    .await()
                    .toObject(Profile::class.java)

                emit(Resource.Success(data = profile!!))
            }
        }
    }.catch {
        emit(Resource.Error(data = null, message = it.localizedMessage ?: "unexpected error"))
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

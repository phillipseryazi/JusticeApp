package com.mudhut.software.justiceapp.domain.repositories.profiles

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

class ProfileRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : IProfileRepository {

    override fun createUserProfile(
        username: String,
        email: String,
        contact: String,
        userType: String,
        avatar: String
    ) = flow {
        emit(Resource.Loading())

        val userMap = mutableMapOf(
            "username" to username,
            "email" to email,
            "contact" to contact,
            "userType" to userType,
            "avatar" to avatar,
            "isVerified" to false
        )

        firebaseAuth.uid?.let {
            firestore.collection("profiles").document(it).set(userMap).await()
        }

        val profile = firebaseAuth.uid?.let {
            firestore.collection("profiles").document(it).get().await()
                .toObject(Profile::class.java)
        }

        emit(Resource.Success(data = profile))

    }.catch {
        emit(Resource.Error(data = null, message = it.localizedMessage ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)
}

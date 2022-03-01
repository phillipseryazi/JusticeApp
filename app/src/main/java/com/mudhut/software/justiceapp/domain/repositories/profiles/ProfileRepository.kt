package com.mudhut.software.justiceapp.domain.repositories.profiles

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mudhut.software.justiceapp.data.models.Profile
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : IProfileRepository {

    override fun createUserProfile(profile: Profile): Flow<Resource<Profile>> = flow {
        emit(Resource.Loading())

        val uid = auth.currentUser?.uid

        val userMap = mutableMapOf(
            "username" to profile.username,
            "uid" to uid,
            "email" to profile.email,
            "contact" to profile.contact,
            "userType" to profile.userType,
            "avatar" to profile.avatar,
            "isVerified" to profile.isVerified
        )

        if (uid != null) {
            firestore.collection("profiles").document(uid).set(userMap).await()

            val userProfile = firestore
                .collection("profiles")
                .document(uid)
                .get()
                .await()
                .toObject(Profile::class.java)

            emit(Resource.Success(data = userProfile!!))
        }
    }.catch {
        emit(Resource.Error(data = null, message = it.localizedMessage ?: "Unknown error"))
    }.flowOn(Dispatchers.IO)
}

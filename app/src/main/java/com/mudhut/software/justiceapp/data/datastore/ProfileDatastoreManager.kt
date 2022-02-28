package com.mudhut.software.justiceapp.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.mudhut.software.justiceapp.LocalProfile
import com.mudhut.software.justiceapp.data.models.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class ProfileDatastoreManager @Inject constructor(private val context: Context) {
    private val tag = "ProfileDatastoreManager"

    companion object {
        private val Context.localProfile: DataStore<LocalProfile> by dataStore(
            fileName = "user_profile.pb",
            serializer = ProfileSerializer
        )
    }

    val readProfile: Flow<LocalProfile> = context
        .localProfile
        .data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(tag, "Error reading sort order preferences", exception)
                emit(LocalProfile.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun updateUsername(username: String) {
        context.localProfile.updateData { profile ->
            profile.toBuilder().setUsername(username).build()
        }
    }

    suspend fun updateUid(uid: String) {
        context.localProfile.updateData { profile ->
            profile.toBuilder().setUid(uid).build()
        }
    }

    suspend fun updateUserType(userType: String) {
        context.localProfile.updateData { profile ->
            profile.toBuilder().setUserType(userType).build()
        }
    }

    suspend fun updateEmail(email: String) {
        context.localProfile.updateData { profile ->
            profile.toBuilder().setEmail(email).build()
        }
    }

    suspend fun updateContact(contact: String) {
        context.localProfile.updateData { profile ->
            profile.toBuilder().setContact(contact).build()
        }
    }

    suspend fun updateAvatar(avatar: String) {
        context.localProfile.updateData { profile ->
            profile.toBuilder().setAvatar(avatar).build()
        }
    }

    suspend fun updateIsVerified(isVerified: Boolean) {
        context.localProfile.updateData { profile ->
            profile.toBuilder().setIsVerified(isVerified).build()
        }
    }

    suspend fun updateLocalProfile(profile: Profile) {
        updateUsername(profile.username)
        updateUid(profile.uid)
        updateUserType(profile.userType)
        updateEmail(profile.email)
        updateContact(profile.contact)
        updateAvatar(profile.avatar)
        updateIsVerified(profile.isVerified)
    }
}

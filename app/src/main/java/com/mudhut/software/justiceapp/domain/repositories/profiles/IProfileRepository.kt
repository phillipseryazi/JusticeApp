package com.mudhut.software.justiceapp.domain.repositories.profiles

import com.mudhut.software.justiceapp.data.models.Profile
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {
    fun createUserProfile(
        username: String,
        email: String,
        contact: String,
        userType: String,
        avatar: String
    ): Flow<Resource<Profile?>>
}

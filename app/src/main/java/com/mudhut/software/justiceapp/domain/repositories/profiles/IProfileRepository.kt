package com.mudhut.software.justiceapp.domain.repositories.profiles

import com.mudhut.software.justiceapp.data.models.Profile
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {
    fun createUserProfile(profile: Profile): Flow<Resource<Profile>>
}

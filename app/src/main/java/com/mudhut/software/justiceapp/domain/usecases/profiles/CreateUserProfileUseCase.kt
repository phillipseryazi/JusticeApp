package com.mudhut.software.justiceapp.domain.usecases.profiles

import com.mudhut.software.justiceapp.data.models.Profile
import com.mudhut.software.justiceapp.domain.repositories.profiles.IProfileRepository
import com.mudhut.software.justiceapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateUserProfileUseCase @Inject constructor(
    private val repository: IProfileRepository
) {
    operator fun invoke(profile: Profile): Flow<Resource<Profile>> = flow {
        repository.createUserProfile(profile).collect {
            emit(it)
        }
    }
}

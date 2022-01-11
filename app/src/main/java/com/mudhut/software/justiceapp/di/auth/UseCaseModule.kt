package com.mudhut.software.justiceapp.di.auth

import com.mudhut.software.justiceapp.domain.repositories.auth.AuthenticationRepository
import com.mudhut.software.justiceapp.domain.usecases.auth.EmailPasswordLoginUseCase
import com.mudhut.software.justiceapp.domain.usecases.auth.EmailPasswordRegistrationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun providesEmailPasswordRegistrationUseCase(repository: AuthenticationRepository) =
        EmailPasswordRegistrationUseCase(repository)

    fun providesEmailPasswordLoginUseCase(repository: AuthenticationRepository) =
        EmailPasswordLoginUseCase(repository)
}

package com.mudhut.software.justiceapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mudhut.software.justiceapp.domain.repositories.auth.AuthenticationRepository
import com.mudhut.software.justiceapp.domain.repositories.auth.IAuthenticationRepository
import com.mudhut.software.justiceapp.domain.repositories.profiles.IProfileRepository
import com.mudhut.software.justiceapp.domain.repositories.profiles.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providesAuthenticationRepository(firebaseAuth: FirebaseAuth) =
        AuthenticationRepository(firebaseAuth) as IAuthenticationRepository

    @Singleton
    @Provides
    fun providesProfileRepository(firestore: FirebaseFirestore, auth: FirebaseAuth) =
        ProfileRepository(firestore, auth) as IProfileRepository

}

package com.mudhut.software.justiceapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.mudhut.software.justiceapp.domain.repositories.auth.AuthenticationRepository
import com.mudhut.software.justiceapp.domain.repositories.auth.IAuthenticationRepository
import com.mudhut.software.justiceapp.domain.repositories.posts.IPostRepository
import com.mudhut.software.justiceapp.domain.repositories.posts.PostRepository
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
    fun providesAuthenticationRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ) = AuthenticationRepository(
        firestore,
        firebaseAuth
    ) as IAuthenticationRepository

    @Singleton
    @Provides
    fun providesProfileRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ) = ProfileRepository(
        firestore,
        firebaseAuth
    ) as IProfileRepository

    @Singleton
    @Provides
    fun providesPostRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        firebaseDatabase: FirebaseDatabase
    ) = PostRepository(
        firebaseAuth,
        firebaseFirestore,
        firebaseDatabase
    ) as IPostRepository

}

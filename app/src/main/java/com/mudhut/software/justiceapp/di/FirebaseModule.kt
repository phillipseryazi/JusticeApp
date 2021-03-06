package com.mudhut.software.justiceapp.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun providesFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun providesFirebaseFirestore() = Firebase.firestore

    @Singleton
    @Provides
    fun providesFirebaseStorage() = Firebase.storage

    @Singleton
    @Provides
    fun providesFirebaseRealtimeDatabase() = Firebase.database
}

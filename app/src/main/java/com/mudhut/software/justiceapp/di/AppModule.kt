package com.mudhut.software.justiceapp.di

import com.google.firebase.auth.FirebaseAuth
import com.mudhut.software.justiceapp.data.NetworkDataSource
import com.mudhut.software.justiceapp.domain.repositories.auth.AuthenticationRepository
import com.mudhut.software.justiceapp.domain.repositories.auth.IAuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesNetworkDataSource(firebaseAuth: FirebaseAuth) = NetworkDataSource(firebaseAuth)

    @Singleton
    @Provides
    fun providesAuthenticationRepository(networkDataSource: NetworkDataSource) =
        AuthenticationRepository(networkDataSource) as IAuthenticationRepository

}

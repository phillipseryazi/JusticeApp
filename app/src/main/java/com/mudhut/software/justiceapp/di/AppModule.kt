package com.mudhut.software.justiceapp.di

import android.content.Context
import androidx.work.WorkManager
import com.mudhut.software.justiceapp.data.datastore.ProfileDatastoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesProfileDatastoreManager(@ApplicationContext context: Context) =
        ProfileDatastoreManager(context)

    @Provides
    fun providesWorkManager(@ApplicationContext context: Context) = WorkManager.getInstance(context)

}

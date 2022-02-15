package com.mudhut.software.justiceapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class JusticeApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        WorkManager.getInstance(this)
        cloudinaryConfig()
    }

    private fun cloudinaryConfig() {
        val config = mapOf(
            "cloud_name" to "mudhut-software",
            "api_key" to "449678456669366",
            "api_secret" to "CqwsXMIDUUqAKxTLaVUk_u8HAPg"
        )
        MediaManager.init(this, config)
    }
}

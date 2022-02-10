package com.mudhut.software.justiceapp.workers

import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.google.firebase.storage.FirebaseStorage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await
import java.util.*

@HiltWorker
class ImageUploadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val storage: FirebaseStorage
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val inputData = inputData.keyValueMap

        val downloadUrl = uploadMedia(
            Uri.parse(inputData.getValue("uri").toString()),
            inputData.getValue("userId").toString()
        )

        val outputData = Data.Builder().putString("downloadUrl", downloadUrl.toString()).build()

        return Result.success(outputData)
    }

    private suspend fun uploadMedia(uri: Uri, userId: String): Uri {
        val reference = storage
            .getReference("media/$userId")
            .child(UUID.randomUUID().toString())

        reference.putFile(uri).await()

        return reference.downloadUrl.await()
    }
}

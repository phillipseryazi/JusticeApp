package com.mudhut.software.justiceapp.ui.camera.helpers

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import com.mudhut.software.justiceapp.utils.FILENAME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("MissingPermission")
class CameraManager(val context: Context) {

    private val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    private val name = "justice-app-recording-" + SimpleDateFormat(FILENAME_FORMAT, Locale.US)
        .format(System.currentTimeMillis()) + ".mp4"

    private val contentValues = ContentValues().apply {
        put(MediaStore.Video.Media.DISPLAY_NAME, name)
    }

    val activeRecording: Recording by lazy {
        getVideoCapture().output
            .prepareRecording(context, getMediaStoreOutput())
            .withAudioEnabled()
            .start(ContextCompat.getMainExecutor(context), videoRecordEventListener)
    }

    private val videoRecordEventListener = Consumer<VideoRecordEvent> {
        when (it) {
            is VideoRecordEvent.Start -> {

            }
            is VideoRecordEvent.Pause -> {

            }
            is VideoRecordEvent.Resume -> {

            }
            is VideoRecordEvent.Finalize -> {
                if (it.hasError()) {
                    Log.e("Recording Error", it.cause?.message.toString())
                } else {
                    it.outputResults.outputUri
                    Log.d("Recording", it.outputResults.outputUri.path.toString())
                }
            }
            is VideoRecordEvent.Status -> {
                val stats: RecordingStats = it.recordingStats
                stats.recordedDurationNanos
            }
        }
    }

    private fun getQualitySelector(): QualitySelector {
        return QualitySelector.fromOrderedList(
            listOf(
                Quality.FHD,
                Quality.HD,
                Quality.SD
            ),
            FallbackStrategy.lowerQualityOrHigherThan(Quality.SD)
        )
    }

    private fun getRecorder(): Recorder {
        return Recorder.Builder()
            .setExecutor(ContextCompat.getMainExecutor(context))
            .setQualitySelector(getQualitySelector())
            .build()
    }

    fun getVideoCapture() = VideoCapture.withOutput(getRecorder())

    fun getCameraProvider(): ProcessCameraProvider = cameraProviderFuture.get()

    private fun getMediaStoreOutput(): MediaStoreOutputOptions {
        return MediaStoreOutputOptions.Builder(
            context.contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        ).setContentValues(contentValues).build()
    }
}

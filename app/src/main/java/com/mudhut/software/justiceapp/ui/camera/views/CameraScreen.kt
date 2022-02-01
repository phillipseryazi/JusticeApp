package com.mudhut.software.justiceapp.ui.camera.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.common.GoToSettingsComposable
import com.mudhut.software.justiceapp.ui.common.PermissionsComposable
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.mudhut.software.justiceapp.utils.FILENAME_FORMAT
import com.mudhut.software.justiceapp.utils.INITIAL_ELAPSED_TIME
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@ExperimentalPermissionsApi
@Composable
fun CameraScreen(goToSettings: () -> Unit) {
    val permissionsList = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val permissionState = rememberMultiplePermissionsState(
        permissions = permissionsList
    )

    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = {
            PermissionsComposable(
                permissions = permissionsList,
                grantPermissions = {
                    permissionState.launchMultiplePermissionRequest()
                })
        },
        permissionsNotAvailableContent = {
            GoToSettingsComposable(
                permissions = permissionsList,
                goToSettings = goToSettings
            )
        }) {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            CameraComposable(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun CameraComposable(modifier: Modifier) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val name = "justice-app-recording-" + SimpleDateFormat(FILENAME_FORMAT, Locale.US)
        .format(System.currentTimeMillis()) + ".mp4"

    val elapsedTime = remember {
        mutableStateOf(INITIAL_ELAPSED_TIME)
    }

    val isRecording = rememberSaveable {
        mutableStateOf(false)
    }

    val isPaused = rememberSaveable {
        mutableStateOf(false)
    }

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    val qualitySelector = remember {
        QualitySelector.fromOrderedList(
            listOf(
                Quality.FHD,
                Quality.HD,
                Quality.SD
            ),
            FallbackStrategy.lowerQualityOrHigherThan(Quality.SD)
        )
    }

    val recorder = remember {
        Recorder.Builder()
            .setExecutor(ContextCompat.getMainExecutor(context))
            .setQualitySelector(qualitySelector)
            .build()
    }

    val videoCapture = remember<VideoCapture<Recorder>> {
        VideoCapture.withOutput(recorder)
    }

    val videoOutputOptions = remember {
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
        }

        MediaStoreOutputOptions.Builder(
            context.contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        ).setContentValues(contentValues).build()
    }

    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    val imageOutputOptions = remember {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/justice-app-image-")
            }
        }

        ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()
    }

    val videoRecordEventListener = remember {
        mutableStateOf<Consumer<VideoRecordEvent>?>(null)
    }

    val pendingRecording = remember {
        mutableStateOf<PendingRecording?>(null)
    }

    val recording = remember {
        mutableStateOf<Recording?>(null)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->

                val previewView = PreviewView(context).apply {
                    this.scaleType = scaleType
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                val preview = androidx.camera.core.Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val cameraProvider = cameraProviderFuture.get()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        videoCapture,
                        imageCapture
                    )
                } catch (exc: Exception) {
                    Log.e("CAMERA", "Use case binding failed", exc)
                }

                previewView
            }
        ) {
            videoRecordEventListener.value = androidx.core.util.Consumer<VideoRecordEvent> {
                when (it) {
                    is VideoRecordEvent.Start -> {

                    }
                    is VideoRecordEvent.Pause -> {

                    }
                    is VideoRecordEvent.Resume -> {

                    }
                    is VideoRecordEvent.Finalize -> {
                        if (it.hasError()) {
                            Log.e("Camera", "Recording Error ${it.cause?.message.toString()}")
                        } else {
                            val videoUri = it.outputResults.outputUri
                            Log.d("Camera", "Recording stats $videoUri")
                        }
                    }
                    is VideoRecordEvent.Status -> {
                        val stats: RecordingStats = it.recordingStats
                        val hh = TimeUnit.NANOSECONDS
                            .toHours(stats.recordedDurationNanos)
                        val mm = TimeUnit.NANOSECONDS
                            .toMinutes(stats.recordedDurationNanos) % 60
                        val ss = TimeUnit.NANOSECONDS
                            .toSeconds(stats.recordedDurationNanos) % 60

                        val time = String.format("%02d:%02d:%02d", hh, mm, ss)
                        elapsedTime.value = time
                        Log.d("Camera", "Recording stats ${stats.recordedDurationNanos}")
                    }
                }
            }

            val activeRecording = videoCapture.output
                .prepareRecording(context, videoOutputOptions)
                .withAudioEnabled()

            pendingRecording.value = activeRecording
        }

        Surface(
            modifier = Modifier
                .padding(
                    start = 32.dp,
                    end = 32.dp,
                    bottom = 32.dp
                )
                .align(Alignment.BottomCenter),
            color = Color.Black,
            shape = RoundedCornerShape(12.dp),
            elevation = 2.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CameraButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp),
                    icon = if (isPaused.value) R.drawable.ic_record_resume else R.drawable.ic_pause,
                    iconColor = if (isPaused.value) Color.Red else Color.White,
                    onButtonClick = {
                        if (isRecording.value) {
                            if (isPaused.value) {
                                recording.value?.resume()
                                isPaused.value = false
                            } else {
                                recording.value?.pause()
                                isPaused.value = true
                            }
                        }
                    }
                )
                CameraButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp),
                    icon = if (isRecording.value) R.drawable.ic_stop else R.drawable.ic_record,
                    iconColor = Color.Red,
                    onButtonClick = {
                        if (isRecording.value) {
                            recording.value?.stop()
                            isRecording.value = false
                        } else {
                            recording.value = videoRecordEventListener.value?.let {
                                pendingRecording.value?.start(
                                    ContextCompat.getMainExecutor(context),
                                    it
                                )
                            }
                            isRecording.value = true
                        }
                    }
                )
                CameraButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp),
                    icon = R.drawable.ic_camera,
                    iconColor = Color.White,
                    onButtonClick = {
                        imageCapture.takePicture(
                            imageOutputOptions,
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onError(exc: ImageCaptureException) {
                                    Log.e(
                                        "Camera",
                                        "Image capture failed: ${exc.message}",
                                        exc
                                    )
                                }

                                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                    Log.d("Camera", "Image Capture: ${output.savedUri}")
                                }
                            }
                        )
                    }
                )
            }
        }
        RecordTimeIndicator(
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.TopCenter),
            elapsedTime = elapsedTime.value
        )
    }
}


@Composable
fun CameraButton(
    modifier: Modifier,
    icon: Int,
    iconColor: Color,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        border = BorderStroke(2.dp, color = Color.White),
        contentPadding = PaddingValues(0.dp),
        onClick = onButtonClick
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = iconColor
        )
    }
}

@Composable
fun RecordTimeIndicator(
    modifier: Modifier,
    elapsedTime: String
) {
    Surface(
        modifier = modifier,
        color = Color.Black,
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        Text(
            elapsedTime,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalPermissionsApi
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CameraScreenPreview() {
    JusticeAppTheme {
        CameraScreen(goToSettings = {})
    }
}

package com.mudhut.software.justiceapp.ui.camera.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.common.PermissionComposable
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.mudhut.software.justiceapp.utils.FILENAME_FORMAT
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalPermissionsApi
@Composable
fun CameraScreen() {
    val permissionsList = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val permissionState = rememberMultiplePermissionsState(
        permissions = permissionsList
    )

    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = {
            PermissionComposable(
                permissions = permissionsList,
                grantPermissions = {
                    permissionState.launchMultiplePermissionRequest()
                })
        },
        permissionsNotAvailableContent = {

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

    val videoRecordEventListener = remember {
        mutableStateOf<Consumer<VideoRecordEvent>?>(null)
    }

    val pendingRecording = remember {
        mutableStateOf<PendingRecording?>(null)
    }

    val recording = remember {
        mutableStateOf<Recording?>(null)
    }

    Box(modifier = modifier) {
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
                        videoCapture
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
                            Log.e("Recording Error", it.cause?.message.toString())
                        } else {
                            it.outputResults.outputUri
                            Log.d("Recording", it.outputResults.outputUri.path.toString())
                        }
                    }
                    is VideoRecordEvent.Status -> {
                        val stats: RecordingStats = it.recordingStats
                        Log.d("Recording Stats", stats.recordedDurationNanos.toString())
                    }
                }
            }

            val name = "justice-app-recording-" + SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".mp4"

            val contentValues = ContentValues().apply {
                put(MediaStore.Video.Media.DISPLAY_NAME, name)
            }

            val mediaStoreOutput = MediaStoreOutputOptions.Builder(
                context.contentResolver,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            ).setContentValues(contentValues).build()

            val activeRecording = videoCapture.output
                .prepareRecording(context, mediaStoreOutput)
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
                    onButtonClick = { }
                )
            }
        }
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

@ExperimentalPermissionsApi
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CameraScreenPreview() {
    JusticeAppTheme {
        CameraScreen()
    }
}

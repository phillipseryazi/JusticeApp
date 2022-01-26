package com.mudhut.software.justiceapp.ui.camera.views

import android.Manifest
import android.content.ContentValues
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
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

@Composable
fun CameraComposable(modifier: Modifier) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    val previewView = remember {
        PreviewView(context).apply {
            id = R.id.camera_preview
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = {
                previewView
            },
            modifier = Modifier.fillMaxSize()
        ) {
            val qualitySelector = QualitySelector.fromOrderedList(
                listOf(
                    Quality.FHD,
                    Quality.HD,
                    Quality.SD
                ),
                FallbackStrategy.lowerQualityOrHigherThan(Quality.SD)
            )

            val recorder = Recorder.Builder()
                .setQualitySelector(qualitySelector)
                .build()

            val videoCapture = VideoCapture.withOutput(recorder)

            val cameraProvider = cameraProviderFuture.get()

            val preview = androidx.camera.core.Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            try {
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    videoCapture
                )
            } catch (exc: Exception) {
                Log.e("CAMERA", "Use case binding failed", exc)
            }

            val videoRecordEventListener = androidx.core.util.Consumer<VideoRecordEvent> {
                when (it) {
                    is VideoRecordEvent.Start -> {

                    }
                    is VideoRecordEvent.Pause -> {

                    }
                    is VideoRecordEvent.Resume -> {

                    }
                    is VideoRecordEvent.Finalize -> {

                    }
                    is VideoRecordEvent.Status -> {
                        val stats: RecordingStats = it.recordingStats
                    }
                }
            }

            val name = "justiceapp-recording-" + SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".mp4"

            val contentValues = ContentValues().apply {
                put(MediaStore.Video.Media.DISPLAY_NAME, name)
            }

            val mediaStoreOutput = MediaStoreOutputOptions.Builder(
                context.contentResolver,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            ).setContentValues(contentValues).build()

            try {
                val activeRecording = videoCapture.output
                    .prepareRecording(context, mediaStoreOutput)
                    .withAudioEnabled()
                    .start(ContextCompat.getMainExecutor(context), videoRecordEventListener)
            } catch (e: SecurityException) {
                Log.e("Camera Exception", e.localizedMessage ?: "")
            }

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
                        .size(40.dp)
                        .clickable { }
                )
                CameraButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                        .clickable { }
                )
                CameraButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                        .clickable { }
                )
            }
        }
    }
}


@Composable
fun CameraButton(
    modifier: Modifier
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(2.dp, color = Color.White)
    ) {

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

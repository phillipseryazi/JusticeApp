package com.mudhut.software.justiceapp.ui.camera.views

import android.Manifest
import android.util.Log
import androidx.camera.core.CameraSelector
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.camera.helpers.CameraManager
import com.mudhut.software.justiceapp.ui.common.PermissionComposable
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme


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

    val isRecording = rememberSaveable {
        mutableStateOf(false)
    }

    val isPaused = rememberSaveable {
        mutableStateOf(false)
    }

    val cameraManager = remember {
        CameraManager(context)
    }

    val previewView = remember {
        PreviewView(context).apply {
            id = R.id.camera_preview
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = {
                val preview = androidx.camera.core.Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                try {
                    cameraManager.getCameraProvider().bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        cameraManager.getVideoCapture()
                    )
                } catch (exc: Exception) {
                    Log.e("CAMERA", "Use case binding failed", exc)
                }

                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

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
                    iconColor = Color.White,
                    onButtonClick = {
                        if (isPaused.value) {
                            cameraManager.activeRecording.resume()
                            isPaused.value = false
                        } else {
                            cameraManager.activeRecording.pause()
                            isPaused.value = true
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
                            cameraManager.activeRecording.stop()
                            isRecording.value = false
                        } else {
                            cameraManager.activeRecording
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

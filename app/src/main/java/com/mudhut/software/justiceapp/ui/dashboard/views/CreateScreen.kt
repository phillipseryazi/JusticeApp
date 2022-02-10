package com.mudhut.software.justiceapp.ui.dashboard.views

import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.common.LoadingScreen
import com.mudhut.software.justiceapp.ui.dashboard.viewmodels.CreateScreenUiState
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.mudhut.software.justiceapp.utils.checkString
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CreateScreen(
    addItemToMediaList: (list: List<Uri>) -> Unit,
    removeItemFromMediaList: (uri: Uri) -> Unit,
    onCaptionChange: (String) -> Unit,
    onPopBackStack: () -> Unit,
    onPostClick: () -> Unit,
    resetMessage: () -> Unit,
    uiState: CreateScreenUiState
) {
    val context = LocalContext.current

    val mediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = {
            addItemToMediaList(it)
        }
    )

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()
            resetMessage()
        }
    }

    if (uiState.isLoading) {
        LoadingScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            TopBarSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onPopBackStack = onPopBackStack,
                onPostClick = onPostClick
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp)
            )
            CaptionSection(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .padding(start = 32.dp, end = 32.dp)
                    .fillMaxWidth(),
                caption = uiState.caption ?: "",
                onCaptionChange = onCaptionChange
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )
            LazyRow(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                items(items = uiState.uris) { item ->
                    MediaCard(uri = item,
                        removeMedia = {
                            removeItemFromMediaList(item)
                        })
                    Log.d("Uri", item.path.toString())
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OpenGalleryButton(
                    modifier = Modifier.size(50.dp),
                    icon = R.drawable.ic_camera,
                    onButtonClick = {
                        mediaLauncher.launch("image/*")
                    })
                OpenGalleryButton(
                    modifier = Modifier.size(50.dp),
                    icon = R.drawable.ic_videocam,
                    onButtonClick = {
                        mediaLauncher.launch("video/*")
                    })
            }
        }
    }
}

@Composable
fun TopBarSection(modifier: Modifier, onPopBackStack: () -> Unit, onPostClick: () -> Unit) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Button(
            modifier = Modifier
                .size(50.dp)
                .padding(start = 8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp
            ),
            contentPadding = PaddingValues(0.dp),
            onClick = onPopBackStack
        ) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize * 2),
                painter = painterResource(id = R.drawable.ic_close),
                tint = Color.Blue,
                contentDescription = null
            )
        }
        Button(
            modifier = Modifier.padding(end = 16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp
            ),
            onClick = onPostClick
        ) {
            Text(
                text = "POST",
                color = Color.White,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CaptionSection(
    modifier: Modifier,
    caption: String,
    onCaptionChange: (String) -> Unit
) {
    TextField(
        label = {
            if (caption.isEmpty()) {
                Text("Add a caption...")
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typography.body2,
        modifier = modifier,
        singleLine = false,
        value = caption,
        onValueChange = onCaptionChange
    )
}

@Composable
fun OpenGalleryButton(
    modifier: Modifier,
    icon: Int,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        border = BorderStroke(1.dp, color = Color.Blue),
        contentPadding = PaddingValues(0.dp),
        elevation = null,
        onClick = onButtonClick,
    ) {
        Icon(
            modifier = Modifier.size(ButtonDefaults.IconSize),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Blue
        )
    }
}


@Composable
fun MediaCard(uri: Uri, removeMedia: () -> Unit) {
    Box(
        modifier = Modifier.size(200.dp, 300.dp)
    ) {
        when (checkString(uri.toString())) {
            1 -> ImageComposable(media = uri, removeMedia = removeMedia)
            2 -> VideoComposable(media = uri, removeMedia = removeMedia)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageComposable(media: Uri, removeMedia: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.White),
        color = Color.Black
    ) {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            GlideImage(
                imageModel = media,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            )

            RemoveMediaButton(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp),
                removeMedia
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VideoComposable(media: Uri, removeMedia: () -> Unit) {
    val context = LocalContext.current

    var isPlaying by remember {
        mutableStateOf(false)
    }

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            this.prepare()
            this.repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.White),
        color = Color.Black
    ) {
        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp))) {
            DisposableEffect(
                AndroidView(factory = { context ->
                    val videoPlayer = PlayerView(context).apply {
                        useController = false
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }

                    videoPlayer.player = player

                    player.setMediaItem(MediaItem.fromUri(media))

                    videoPlayer
                })
            ) {
                onDispose { player.release() }
            }

            RemoveMediaButton(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp),
                removeMedia
            )

            PlayPauseButton(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                isPlaying = isPlaying,
                onButtonClick = {
                    if (player.isPlaying) {
                        isPlaying = !isPlaying
                        player.pause()
                    } else {
                        isPlaying = !isPlaying
                        player.play()
                    }
                })
        }
    }
}

@Composable
fun RemoveMediaButton(modifier: Modifier, removeMedia: () -> Unit) {
    Button(
        shape = CircleShape,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black
        ),
        contentPadding = PaddingValues(0.dp),
        onClick = removeMedia
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            tint = Color.White,
            contentDescription = null
        )
    }
}

@Composable
fun PlayPauseButton(
    modifier: Modifier,
    isPlaying: Boolean,
    onButtonClick: () -> Unit
) {
    Button(
        shape = CircleShape,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isPlaying) Color.Transparent else Color.Blue
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        onClick = onButtonClick
    ) {
        Icon(
            painter = if (isPlaying) painterResource(id = R.drawable.ic_pause)
            else painterResource(id = R.drawable.ic_play),
            tint = Color.White,
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun MediaCardPreview() {
    JusticeAppTheme {
        CreateScreen(
            addItemToMediaList = {},
            removeItemFromMediaList = {},
            onCaptionChange = {},
            onPopBackStack = {},
            onPostClick = {},
            resetMessage = {},
            uiState = CreateScreenUiState()
        )
    }
}

package com.mudhut.software.justiceapp.ui.dashboard.views

import android.net.Uri
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.dashboard.viewmodels.CreateScreenUiState
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CreateScreen(
    addItemToMediaList: (list: List<Uri>) -> Unit,
    removeItemFromMediaList: (uri: Uri) -> Unit,
    uiState: CreateScreenUiState
) {
    var caption by remember {
        mutableStateOf("")
    }

    val mediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = {
            addItemToMediaList(it)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        CaptionSection(
            modifier = Modifier
                .background(color = Color.Transparent)
                .padding(start = 32.dp, end = 32.dp)
                .fillMaxWidth(),
            caption = caption,
            onValueChanged = { caption = it }
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
                MediaCard(image = item,
                    removeMedia = {
                        removeItemFromMediaList(item)
                    })
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

@Composable
fun CaptionSection(
    modifier: Modifier,
    caption: String,
    onValueChanged: (String) -> Unit
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
        onValueChange = onValueChanged
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
fun MediaCard(image: Uri, removeMedia: () -> Unit) {
    Box(
        modifier = Modifier.size(200.dp, 300.dp)
    ) {
        ImageComposable(media = image, removeMedia = removeMedia)
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

            Button(
                shape = CircleShape,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp),
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

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VideoComposable() {
    Surface(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.White),
        color = Color.Black,
        onClick = {}
    ) {

    }
}

@Preview
@Composable
fun MediaCardPreview() {
    JusticeAppTheme {
        CreateScreen(
            addItemToMediaList = {},
            removeItemFromMediaList = {},
            uiState = CreateScreenUiState()
        )
    }
}

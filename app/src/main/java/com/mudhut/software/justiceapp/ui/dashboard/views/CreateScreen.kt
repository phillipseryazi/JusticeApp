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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CreateScreen() {
    var caption by remember {
        mutableStateOf("")
    }

    var media = remember {
        mutableStateOf<List<Uri>?>(null)
    }

    val mediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = {
//            media.add(it)
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
        CaptionSection(caption = caption, onValueChanged = { caption = it })
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )
        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxWidth()
        ) {
//            items(items = media ?: listOf()) { item ->
//                MediaCard(image = item)
//            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OpenGalleryButton(icon = R.drawable.ic_camera, onButtonClick = {
                mediaLauncher.launch("image/*")
            })
            OpenGalleryButton(icon = R.drawable.ic_videocam, onButtonClick = {
                mediaLauncher.launch("video/*")
            })
        }
    }
}

@Composable
fun CaptionSection(caption: String, onValueChanged: (String) -> Unit) {
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
        modifier = Modifier
            .background(color = Color.Transparent)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        singleLine = false,
        value = caption,
        onValueChange = onValueChanged
    )
}

@Composable
fun OpenGalleryButton(icon: Int, onButtonClick: () -> Unit) {
    Button(
        modifier = Modifier
            .size(50.dp)
            .padding(top = 8.dp, bottom = 8.dp),
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
fun MediaCard(image: Uri) {
    Box(
        modifier = Modifier.size(200.dp, 300.dp)
    ) {
        ImageComposable(media = image)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageComposable(media: Uri) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.White),
        color = Color.Black,
        onClick = {}
    ) {
        GlideImage(
            imageModel = media,
            contentScale = ContentScale.Crop
        )
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
        CreateScreen()
    }
}

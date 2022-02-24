package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CommentScreen(
    postComment: (postId: String, comment: String) -> Unit,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBarSectionComposable(navigateBack = {})
    }
}

@Composable
fun TopBarSectionComposable(navigateBack: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            modifier = Modifier
                .size(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            contentPadding = PaddingValues(0.dp),
            onClick = navigateBack
        ) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize * 2),
                painter = painterResource(
                    id = R.drawable.ic_close
                ),
                tint = Color.Blue,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            stringResource(R.string.text_comment),
            modifier = Modifier.align(Alignment.CenterVertically),
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Blue,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CommentListSectionComposable() {

}

@Composable
fun CommentComposable(avatar: Int, author: String, comment: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row() {
            GlideImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                imageModel = avatar,
                contentScale = ContentScale.Fit,
            )
            Column {
                Text(
                    author,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    comment,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}


@Preview
@Composable
fun CommentScreenPreview() {
    JusticeAppTheme {
        CommentScreen(postComment = { postId, comment -> }, navigateBack = {})
    }
}

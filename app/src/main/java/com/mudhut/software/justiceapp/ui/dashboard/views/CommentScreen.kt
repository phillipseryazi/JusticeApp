package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.mudhut.software.justiceapp.data.models.Comment
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CommentScreen(
    getComments: (postId: String) -> Unit,
    postComment: (postId: String, comment: String) -> Unit,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBarSectionComposable(navigateBack = navigateBack)
    }
}

@Composable
fun TopBarSectionComposable(navigateBack: () -> Unit) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                stringResource(R.string.text_comment),
                style = MaterialTheme.typography.h1,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Blue,
                textAlign = TextAlign.Center,
            )
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    modifier = Modifier
                        .size(ButtonDefaults.IconSize * 2),
                    painter = painterResource(
                        id = R.drawable.ic_close
                    ),
                    tint = Color.Blue,
                    contentDescription = null
                )
            }
        },
        backgroundColor = Color.White,
        elevation = 2.dp
    )
}

@Composable
fun CommentListSectionComposable(comments: List<Comment>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp)
    ) {
        itemsIndexed(comments) { index, item ->
            CommentComposable(
                avatar = item.author.avatar,
                author = item.author.username,
                comment = item.content
            )
        }
    }
}

@Composable
fun CommentComposable(avatar: String, author: String, comment: String) {
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

@Composable
fun WriteCommentSectionComposable() {

}


@Preview
@Composable
fun CommentScreenPreview() {
    JusticeAppTheme {
        CommentScreen(
            postComment = { postId, comment -> },
            getComments = { postId -> },
            navigateBack = {}
        )
    }
}

package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.data.models.Comment
import com.mudhut.software.justiceapp.ui.dashboard.viewmodels.CommentScreenUiState
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CommentScreen(
    getComments: (postId: String) -> Unit,
    postComment: (postId: String, comment: String) -> Unit,
    navigateBack: () -> Unit,
    uiState: CommentScreenUiState
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (topBarSection, commentsSection, addCommentSection) = createRefs()

        TopBarSectionComposable(
            modifier = Modifier.constrainAs(topBarSection) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            navigateBack = navigateBack
        )
        CommentListSectionComposable(
            modifier = Modifier.constrainAs(commentsSection) {
                start.linkTo(parent.start)
                top.linkTo(topBarSection.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(addCommentSection.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            comments = uiState.comments
        )
        WriteCommentSectionComposable(
            modifier = Modifier.constrainAs(addCommentSection) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 12.dp)
                width = Dimension.fillToConstraints
            },
            newComment = uiState.newComment,
            postComment = {
                postComment("", "")
            }
        )
    }
}

@Composable
fun TopBarSectionComposable(
    modifier: Modifier,
    navigateBack: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                stringResource(R.string.text_comments),
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
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

@Composable
fun CommentListSectionComposable(
    modifier: Modifier,
    comments: List<Comment>
) {
    LazyColumn(
        modifier = modifier,
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
fun CommentComposable(
    avatar: String,
    author: String,
    comment: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            GlideImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                imageModel = avatar,
                contentScale = ContentScale.Fit,
                placeHolder = ImageBitmap.imageResource(
                    id = R.drawable.person_placeholder
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    modifier = Modifier.width(200.dp),
                    text = author,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.width(200.dp),
                    text = comment,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
fun WriteCommentSectionComposable(
    modifier: Modifier,
    newComment: String,
    postComment: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            label = {
                if (newComment.isEmpty()) {
                    Text("Add a comment")
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(204, 204, 204, 60),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            value = "",
            singleLine = false,
            textStyle = MaterialTheme.typography.body2,
            onValueChange = {}
        )
        Button(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            onClick = postComment
        ) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = R.drawable.ic_send),
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}


@Preview
@Composable
fun CommentScreenPreview() {
    JusticeAppTheme {
        CommentScreen(
            postComment = { postId, comment -> },
            getComments = { postId -> },
            navigateBack = {},
            uiState = CommentScreenUiState()
        )
    }
}

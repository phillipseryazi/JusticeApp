package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.ui.common.ImageViewerComposable
import com.mudhut.software.justiceapp.ui.common.VideoPlayerComposable
import com.mudhut.software.justiceapp.ui.dashboard.viewmodels.HomeScreenUiState
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.mudhut.software.justiceapp.utils.checkString
import com.mudhut.software.justiceapp.utils.simplifyCount

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    upVotePost: (postId: String, pos: Int) -> Unit,
    unVotePost: (postId: String, pos: Int) -> Unit,
    getPostComments: (postId: String, page: Int) -> Unit,
    uiState: HomeScreenUiState
) {
    HomeScreenContentParent(
        modifier = Modifier.fillMaxSize(),
        upVotePost = upVotePost,
        unVotePost = unVotePost,
        uiState = uiState,
        onCommentsClicked = {}
    )
}

@Composable
fun HomeScreenContentParent(
    modifier: Modifier,
    upVotePost: (postId: String, pos: Int) -> Unit,
    unVotePost: (postId: String, pos: Int) -> Unit,
    onCommentsClicked: (postId: String) -> Unit,
    uiState: HomeScreenUiState,
) {
    Box(
        modifier = modifier
            .background(Color.Black)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colors.primary
            )
        } else {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(0.dp)
            ) {
                itemsIndexed(uiState.posts) { index, item ->
                    if (item != null) {
                        HomeScreenItemComposable(
                            modifier = Modifier.fillParentMaxSize(),
                            post = item,
                            onVoteClicked = {
                                if (item.isUpvoted) {
                                    unVotePost(item.key, index)
                                } else {
                                    upVotePost(item.key, index)
                                }
                            },
                            onCommentsClicked = { onCommentsClicked(item.key) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenItemComposable(
    modifier: Modifier,
    post: Post,
    onVoteClicked: () -> Unit,
    onCommentsClicked: () -> Unit
) {
    val pagerState = rememberPagerState()

    Box(modifier = modifier) {
        HorizontalPager(
            modifier = modifier,
            count = post.media.size,
            state = pagerState
        ) { page ->
            when (checkString(post.media[page])) {
                1 -> ImageViewerComposable(
                    modifier = modifier,
                    media = post.media[page]
                )
                2 -> VideoPlayerComposable(
                    modifier = modifier,
                    media = post.media[page]
                )
            }
        }

        HomeScreenInteractionSection(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
            upVotes = post.upvote_count,
            commentCount = post.comment_count,
            isUpvoted = post.isUpvoted,
            onVoteClicked = onVoteClicked,
            onCommentsClicked = onCommentsClicked
        )

        HomeScreenInformationSection(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 120.dp),
            author = post.author,
            caption = post.caption
        )
    }
}

@Composable
fun HomeScreenInteractionSection(
    modifier: Modifier,
    upVotes: Int,
    commentCount: Int,
    isUpvoted: Boolean,
    onVoteClicked: () -> Unit,
    onCommentsClicked: () -> Unit
) {
    Column(modifier) {
        IconButtonAndLabel(
            modifier = modifier,
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_up),
                    tint = if (isUpvoted) Color.Green else Color.White,
                    contentDescription = null
                )
            },
            text = simplifyCount(upVotes),
            onButtonClick = onVoteClicked
        )
        Spacer(modifier = Modifier.height(16.dp))
        IconButtonAndLabel(
            modifier = modifier,
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_comments),
                    tint = Color.White,
                    contentDescription = null
                )
            },
            text = simplifyCount(commentCount),
            onButtonClick = onCommentsClicked
        )
    }
}


@Composable
fun IconButtonAndLabel(
    modifier: Modifier,
    icon: @Composable () -> Unit,
    text: String,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent
            ),
            contentPadding = PaddingValues(0.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = onButtonClick
        ) {
            icon()
        }
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HomeScreenInformationSection(modifier: Modifier, author: String, caption: String) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.width(200.dp),
            text = author,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.width(200.dp),
            text = caption,
            style = MaterialTheme.typography.body1,
            fontSize = 12.sp,
            color = Color.White,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview
@Composable
fun HomeScreenInteractionSectionPreview() {
    JusticeAppTheme {
        HomeScreenInteractionSection(
            modifier = Modifier,
            310000,
            25000000,
            isUpvoted = true,
            onVoteClicked = {},
            onCommentsClicked = {}
        )
    }
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    JusticeAppTheme {
//        HomeScreen(uiState = HomeScreenUiState())
//    }
//}

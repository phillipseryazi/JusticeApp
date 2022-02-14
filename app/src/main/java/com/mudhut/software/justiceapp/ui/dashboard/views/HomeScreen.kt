package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun HomeScreen(
    uiState: HomeScreenUiState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp)
    ) {
        items(uiState.posts) {
            HomeScreenItemComposable(
                modifier = Modifier.fillParentMaxSize(),
                post = it
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenItemComposable(
    modifier: Modifier,
    post: Post
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
            onUpVoteClicked = {},
            onCommentsClicked = {}
        )
    }
}

@Composable
fun HomeScreenInteractionSection(
    modifier: Modifier,
    upVotes: Int,
    commentCount: Int,
    onUpVoteClicked: () -> Unit,
    onCommentsClicked: () -> Unit
) {
    Column(modifier) {
        IconButtonAndLabel(
            modifier = modifier,
            icon = R.drawable.ic_thumb_up,
            text = upVotes.toString()
        ) {
            onUpVoteClicked()
        }
        Spacer(modifier = Modifier.height(16.dp))
        IconButtonAndLabel(
            modifier = modifier,
            icon = R.drawable.ic_comments,
            text = commentCount.toString()
        ) {
            onCommentsClicked()
        }
    }
}

@Composable
fun IconButtonAndLabel(
    modifier: Modifier,
    icon: Int,
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
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize * 2),
                painter = painterResource(id = icon),
                tint = Color.White,
                contentDescription = null
            )
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
fun HomeScreenInformationSection(modifier: Modifier) {

}

@Preview
@Composable
fun HomeScreenInteractionSectionPreview() {
    JusticeAppTheme {
        HomeScreenInteractionSection(
            modifier = Modifier,
            299,
            1000,
            onUpVoteClicked = {},
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

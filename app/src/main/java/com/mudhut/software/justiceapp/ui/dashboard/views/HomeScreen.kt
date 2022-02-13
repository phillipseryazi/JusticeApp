package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
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
                modifier = Modifier.fillMaxSize(),
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
        HorizontalPager(count = post.media.size, state = pagerState) { page ->
            when (checkString(post.media[page].toString())) {
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
            modifier = modifier,
            upVotes = post.upvote_count,
            commentCount = post.comment_count
        )
    }
}

@Composable
fun HomeScreenInteractionSection(
    modifier: Modifier,
    upVotes: Int,
    commentCount: Int
) {

}

@Composable
fun HomeScreenInformationSection(modifier: Modifier) {

}

@Preview
@Composable
fun HomeScreenPreview() {
    JusticeAppTheme {
        HomeScreen(uiState = HomeScreenUiState())
    }
}

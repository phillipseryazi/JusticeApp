package com.mudhut.software.justiceapp.onboarding.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.onboarding.models.OnBoardingStep
import com.mudhut.software.justiceapp.onboarding.models.getOnBoardingSteps
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme

@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val steps = getOnBoardingSteps(context = context)

    val scaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                HorizontalPager(
                    count = steps.size,
                    state = pagerState
                ) { page ->
                    OnBoardingInformationSection(step = steps[page])
                }
                OnBoardingPageIndicator(state = pagerState)
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                if (pagerState.currentPage == 3) {
                    Button(

                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White
                        ),
                        onClick = {
                            navigateToLogin()
                        },
                        modifier = Modifier
                            .size(width = 140.dp, height = 50.dp)
                            .align(Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            context.getString(R.string.get_started),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                )
            }

        }
    }
}

@Composable
fun OnBoardingInformationSection(step: OnBoardingStep) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {
        Image(
            painter = painterResource(id = step.illustration),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = 16.dp,
                    start = 16.dp
                )
                .height(250.dp),
            alignment = Alignment.BottomCenter,
            contentScale = ContentScale.Inside
        )
        Text(
            text = step.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1
        )
        Text(
            text = step.detail,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
    }
}

@ExperimentalPagerApi
@Composable
fun OnBoardingPageIndicator(state: PagerState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalPagerIndicator(pagerState = state)
    }
}

@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    JusticeAppTheme {
        OnBoardingScreen(navigateToLogin = {})
    }
}

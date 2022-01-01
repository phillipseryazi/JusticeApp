package com.mudhut.software.justiceapp.onboarding.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.onboarding.models.OnBoardingStep
import com.mudhut.software.justiceapp.onboarding.models.getOnBoardingSteps
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme

@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(
) {
    val context = LocalContext.current
    val step = getOnBoardingSteps(context = context)

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            OnBoardingInformationSection(step = step[0])
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    Text(context.getString(R.string.text_back))
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    Text(context.getString(R.string.text_next))
                }
            }
        }
    }
}

@Composable
fun OnBoardingInformationSection(step: OnBoardingStep) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = step.illustration),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, end = 16.dp, start = 16.dp)
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
@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    JusticeAppTheme {
        OnBoardingScreen()
    }
}

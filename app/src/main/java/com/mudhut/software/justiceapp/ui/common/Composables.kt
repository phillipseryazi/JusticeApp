package com.mudhut.software.justiceapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mudhut.software.justiceapp.R

@Composable
fun ErrorBanner(string: String) {
    Text(string, color = MaterialTheme.colors.error, fontSize = 12.sp)
}

@Composable
fun LoadingPage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_blm_skateboarder),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    end = 16.dp,
                    start = 16.dp
                )
                .height(250.dp)
        )
        LinearProgressIndicator(modifier = Modifier.width(200.dp))
    }
}

@Preview
@Composable
fun LoadingPagePreview() {
    LoadingPage()
}

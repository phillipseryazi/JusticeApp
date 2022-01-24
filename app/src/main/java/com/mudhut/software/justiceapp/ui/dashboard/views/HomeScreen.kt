package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mudhut.software.justiceapp.R

@Composable
fun HomeScreen(navigateToCameraScreen: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            shape = CircleShape,
            contentColor = Color.White,
            onClick = navigateToCameraScreen,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 70.dp, start = 24.dp)
        )
        {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = null
            )
        }
    }
}

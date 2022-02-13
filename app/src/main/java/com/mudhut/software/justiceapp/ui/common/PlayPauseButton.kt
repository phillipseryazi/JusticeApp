package com.mudhut.software.justiceapp.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mudhut.software.justiceapp.R

@Composable
fun PlayPauseButton(
    modifier: Modifier,
    isPlaying: Boolean,
    onButtonClick: () -> Unit
) {
    Button(
        shape = CircleShape,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isPlaying) Color.Transparent else Color.Blue
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        onClick = onButtonClick
    ) {
        Icon(
            painter = if (isPlaying) painterResource(id = R.drawable.ic_pause)
            else painterResource(id = R.drawable.ic_play),
            tint = Color.White,
            contentDescription = null
        )
    }
}
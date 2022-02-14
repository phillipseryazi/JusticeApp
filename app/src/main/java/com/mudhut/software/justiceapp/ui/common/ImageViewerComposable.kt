package com.mudhut.software.justiceapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageViewerComposable(modifier: Modifier, media: String) {
    Box(
        modifier = modifier.background(color = Color.Black)
    ) {
        GlideImage(
            imageModel = media,
            contentScale = ContentScale.Fit
        )
    }
}

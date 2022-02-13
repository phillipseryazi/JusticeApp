package com.mudhut.software.justiceapp.ui.common

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageViewerComposable(modifier: Modifier, media: Uri) {
    Box(
        modifier = modifier
    ) {
        GlideImage(
            imageModel = media,
            contentScale = ContentScale.Fit
        )
    }
}

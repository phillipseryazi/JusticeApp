package com.mudhut.software.justiceapp.ui.common

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageViewerComposable(media: Uri) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(12.dp))
    ) {
        GlideImage(
            imageModel = media,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        )
    }
}

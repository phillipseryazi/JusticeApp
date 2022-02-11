package com.mudhut.software.justiceapp.ui.common

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.mudhut.software.justiceapp.ui.dashboard.views.PlayPauseButton

@Composable
fun VideoPlayerComposable(
    modifier: Modifier,
    media: Uri,
    isPlaying: Boolean,
    onPlayPauseButtonClicked: () -> Unit
) {
    val context = LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            this.prepare()
            this.repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    Box(modifier = modifier) {
        DisposableEffect(
            AndroidView(factory = { context ->
                val videoPlayer = PlayerView(context).apply {
                    useController = false
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                videoPlayer.player = player

                player.setMediaItem(MediaItem.fromUri(media))

                videoPlayer
            })
        ) {
            onDispose { player.release() }
        }

        PlayPauseButton(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center),
            isPlaying = isPlaying,
            onButtonClick = {
                if (player.isPlaying) {
                    player.pause()
                    onPlayPauseButtonClicked()
                } else {
                    player.play()
                    onPlayPauseButtonClicked()
                }
            })
    }
}

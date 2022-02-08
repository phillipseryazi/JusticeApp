package com.mudhut.software.justiceapp.ui.dashboard.viewmodels

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

data class CreateScreenUiState(
    val uris: List<Uri> = mutableStateListOf(),
    var caption: String = "",
    var error: String = ""
)

@HiltViewModel
class CreateScreenViewModel @Inject constructor() : ViewModel() {

    var uiState = MutableStateFlow(CreateScreenUiState())
        private set

    private val mediaList = mutableStateListOf<Uri>()

    fun addUriToMediaList(uris: List<Uri>) {
        uris.forEach {
            mediaList.add(it)
            uiState.value = uiState.value.copy(uris = mediaList)
        }
    }

    fun removeUriFromMediaList(uri: Uri) {
        if (uiState.value.uris.contains(uri)) {
            mediaList.remove(uri)
        }
        uiState.value = uiState.value.copy(uris = mediaList)
    }

    fun postItem() {
        if (uiState.value.uris.isEmpty() && uiState.value.caption.trim().isEmpty()) {
            uiState.value = uiState.value.copy(error = "Can't upload empty post")
        }

        if (uiState.value.caption.trim().isEmpty()) {
            uiState.value = uiState.value.copy(error = "Please add a caption.")
        }

    }
}

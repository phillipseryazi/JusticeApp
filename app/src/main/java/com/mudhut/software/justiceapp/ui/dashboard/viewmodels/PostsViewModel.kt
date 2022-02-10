package com.mudhut.software.justiceapp.ui.dashboard.viewmodels

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.domain.usecases.dashboard.CreatePostUseCase
import com.mudhut.software.justiceapp.utils.NO_CAPTION_MESSAGE
import com.mudhut.software.justiceapp.utils.NO_MEDIA_MESSAGE
import com.mudhut.software.justiceapp.utils.POST_UPLOADED_MESSAGE
import com.mudhut.software.justiceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateScreenUiState(
    val uris: List<Uri> = mutableStateListOf(),
    var caption: String? = null,
    var message: String? = null,
    var isLoading: Boolean = false
)

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

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

    fun onCaptionChange(caption: String) {
        uiState.value = uiState.value.copy(caption = caption)
    }

    fun postItem() {
        when {
            uiState.value.caption?.trim()?.isEmpty() == true -> {
                uiState.value = uiState.value.copy(message = NO_CAPTION_MESSAGE)
            }
            uiState.value.uris.isEmpty() -> {
                uiState.value = uiState.value.copy(message = NO_MEDIA_MESSAGE)
            }
            else -> {
                val post = Post(
                    caption = uiState.value.caption ?: "",
                    media = uiState.value.uris.ifEmpty { listOf() },
                    created_at = System.currentTimeMillis().toString()
                )

                viewModelScope.launch(Dispatchers.IO) {
                    createPostUseCase.invoke(post).collect {
                        when (it) {
                            is Resource.Loading -> {
                                uiState.value = uiState.value.copy(isLoading = true)
                            }
                            is Resource.Success -> {
                                uiState.value = uiState.value.copy(
                                    message = POST_UPLOADED_MESSAGE,
                                    isLoading = false
                                )
                            }
                            is Resource.Error -> {
                                it.message?.let { msg ->
                                    uiState.value = uiState.value.copy(
                                        message = msg,
                                        isLoading = false
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun resetMessage() {
        uiState.value = uiState.value.copy(message = null)
    }
}

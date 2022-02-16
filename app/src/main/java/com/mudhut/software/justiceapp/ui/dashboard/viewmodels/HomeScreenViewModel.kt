package com.mudhut.software.justiceapp.ui.dashboard.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.domain.usecases.dashboard.GetPostsUseCase
import com.mudhut.software.justiceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenUiState(
    val posts: List<Post?> = listOf(),
    val isLoading: Boolean = false,
    val message: String = ""
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val tag = "HomeScreenViewModel"

    var uiState = MutableStateFlow(HomeScreenUiState())
        private set

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase.invoke().collect {
                when (it) {
                    is Resource.Loading -> {
                        uiState.value = uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            posts = it.data ?: listOf()
                        )
                    }
                    is Resource.Error -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            message = it.message ?: "Unknown error"
                        )
                        it.message?.let { err -> Log.e(tag, "Error: $err") }
                    }
                }
            }
        }
    }
}

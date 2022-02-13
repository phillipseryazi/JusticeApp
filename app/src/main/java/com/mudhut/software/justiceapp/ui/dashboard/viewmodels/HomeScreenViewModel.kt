package com.mudhut.software.justiceapp.ui.dashboard.viewmodels

import androidx.lifecycle.ViewModel
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.domain.usecases.dashboard.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class HomeScreenUiState(
    val posts: List<Post> = listOf(),
    val isLoading: Boolean = false
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {
}

package com.mudhut.software.justiceapp.ui.dashboard.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.software.justiceapp.data.models.Post
import com.mudhut.software.justiceapp.domain.usecases.posts.GetPostsUseCase
import com.mudhut.software.justiceapp.domain.usecases.posts.UnVotePostUseCase
import com.mudhut.software.justiceapp.domain.usecases.posts.UpVotePostUseCase
import com.mudhut.software.justiceapp.utils.Resource
import com.mudhut.software.justiceapp.utils.UNKNOWN_ERROR_MESSAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeScreenUiState(
    val posts: MutableList<Post?> = mutableListOf(),
    val isLoading: Boolean = false,
    val message: String = "",
    val isUpVoted: Boolean = false
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val upVotePostUseCase: UpVotePostUseCase,
    private val unVotePostUseCase: UnVotePostUseCase
) : ViewModel() {

    private val tag = "HomeScreenViewModel"

    var uiState = MutableStateFlow(HomeScreenUiState())
        private set

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase().collect {
                when (it) {
                    is Resource.Loading -> {
                        uiState.value = uiState.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            posts = it.data?.toMutableList() ?: mutableListOf()
                        )
                    }
                    is Resource.Error -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            message = it.message ?: UNKNOWN_ERROR_MESSAGE
                        )
                        it.message?.let { err -> Log.e(tag, err) }
                    }
                }
            }
        }
    }

    fun upVotePost(postId: String, pos: Int) {
        viewModelScope.launch {
            upVotePostUseCase(postId).collect {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        updateScreenOnUpVote(uiState.value.posts, pos)
                    }
                    is Resource.Error -> {
                        uiState.value = uiState.value.copy(
                            message = it.message ?: UNKNOWN_ERROR_MESSAGE
                        )
                    }
                }
            }
        }
    }

    private fun updateScreenOnUpVote(list: MutableList<Post?>, pos: Int) {
        list[pos]?.upvote_count = list[pos]?.upvote_count?.plus(1)!!
        list[pos]?.isUpvoted = true

        uiState.value = uiState.value.copy(
            posts = list,
            message = "Post upvoted"
        )
    }

    fun unVotePost(postId: String, pos: Int) {
        viewModelScope.launch {
            unVotePostUseCase(postId).collect {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        updateScreenOnUnVote(uiState.value.posts, pos)
                    }
                    is Resource.Error -> {
                        uiState.value = uiState.value.copy(
                            message = it.message ?: UNKNOWN_ERROR_MESSAGE
                        )
                    }
                }
            }
        }
    }

    private fun updateScreenOnUnVote(list: MutableList<Post?>, pos: Int) {
        list[pos]?.upvote_count = list[pos]?.upvote_count?.minus(1) ?: 0
        list[pos]?.isUpvoted = false

        uiState.value = uiState.value.copy(
            posts = list,
            message = "Post unvoted"
        )
    }
}


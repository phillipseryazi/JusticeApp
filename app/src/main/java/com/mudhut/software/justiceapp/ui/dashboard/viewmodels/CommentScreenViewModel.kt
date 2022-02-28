package com.mudhut.software.justiceapp.ui.dashboard.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.software.justiceapp.LocalProfile
import com.mudhut.software.justiceapp.data.datastore.ProfileDatastoreManager
import com.mudhut.software.justiceapp.data.models.Comment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CommentScreenUiState(
    var isLoading: Boolean = false,
    var comments: List<Comment> = mutableStateListOf()
)

@HiltViewModel
class CommentScreenViewModel @Inject constructor(
    private val datastore: ProfileDatastoreManager
) : ViewModel() {
    private val tag = "CommentScreenViewModel"

    var uiState = MutableStateFlow(CommentScreenUiState())
        private set

    private suspend fun getLocalProfile(): LocalProfile {
        return try {
            datastore.readProfile.first()
        } catch (e: Exception) {
            Log.e(tag, "Datastore Error", e)
            LocalProfile.getDefaultInstance()
        }
    }

    fun getComments(postId: String, page: Int) {
        viewModelScope.launch {

        }
    }

    fun postComments(postId: String, content: String) {
        viewModelScope.launch {

        }
    }
}

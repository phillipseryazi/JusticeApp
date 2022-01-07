package com.mudhut.software.justiceapp.authentication.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mudhut.software.justiceapp.utils.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface AuthUiState {
    var email: String
    var password: String
    var loading: Boolean
    var error: String

    data class RegistrationUiState(
        override var email: String = "",
        override var password: String = "",
        override var loading: Boolean = false,
        override var error: String = "",
        var username: String = "",
        var contact: String = "",
        var userType: UserType? = null,
    ) : AuthUiState

    data class LoginUiState(
        override var email: String = "",
        override var password: String = "",
        override var loading: Boolean = false,
        override var error: String = "",
    ) : AuthUiState
}


@HiltViewModel
class AuthenticationViewModel @Inject constructor() : ViewModel() {
    var registrationUiState = mutableStateOf(AuthUiState.RegistrationUiState())
        private set

    var loginUiState = mutableStateOf(AuthUiState.LoginUiState())
        private set

    fun changeRegistrationUsername(username: String) {
        val copy = registrationUiState.value.copy(username = username)
        registrationUiState.value = copy
    }

    fun changeRegistrationEmail(email: String) {
        val copy = registrationUiState.value.copy(email = email)
        registrationUiState.value = copy
    }

    fun changeRegistrationContact(contact: String) {
        val copy = registrationUiState.value.copy(contact = contact)
        registrationUiState.value = copy
    }

    fun changeRegistrationPassword(password: String) {
        val copy = registrationUiState.value.copy(password = password)
        registrationUiState.value = copy
    }

    fun changeUserType(userType: UserType) {
        val copy = registrationUiState.value.copy(userType = userType)
        registrationUiState.value = copy
    }

    fun changeLoginEmail(email: String) {
        val copy = loginUiState.value.copy(email = email)
        loginUiState.value = copy
    }

    fun changeLoginPassword(password: String) {
        val copy = loginUiState.value.copy(password = password)
        loginUiState.value = copy
    }

    fun emailPasswordLogin() {

    }

    fun registerUser() {

    }


}

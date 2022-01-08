package com.mudhut.software.justiceapp.ui.authentication.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mudhut.software.justiceapp.utils.InputValidator
import com.mudhut.software.justiceapp.utils.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface AuthUiState {
    var email: String
    var password: String
    var loading: Boolean
    var error: String?

    data class RegistrationUiState(
        override var email: String = "",
        override var password: String = "",
        override var loading: Boolean = false,
        override var error: String? = null,
        var username: String = "",
        var contact: String = "",
        var userType: UserType? = null,
    ) : AuthUiState

    data class LoginUiState(
        override var email: String = "",
        override var password: String = "",
        override var loading: Boolean = false,
        override var error: String? = null,
    ) : AuthUiState
}


@HiltViewModel
class AuthenticationViewModel @Inject constructor() : ViewModel() {
    var uiError: String? = null
    var stateCopy: AuthUiState? = null

    var registrationUiState = mutableStateOf(AuthUiState.RegistrationUiState())
        private set

    var loginUiState = mutableStateOf(AuthUiState.LoginUiState())
        private set

    fun changeRegistrationUsername(username: String) {
        uiError = InputValidator.TextFieldValidator.validate(username, null)
        if (uiError != null) {
            stateCopy = registrationUiState.value.copy(username = username, error = uiError)
            registrationUiState.value = stateCopy as AuthUiState.RegistrationUiState
        } else {
            stateCopy = registrationUiState.value.copy(username = username, error = uiError)
            registrationUiState.value = stateCopy as AuthUiState.RegistrationUiState
        }
    }

    fun changeRegistrationEmail(email: String) {
        uiError = InputValidator.EmailValidator.validate(email, null)
        if (uiError != null) {
            stateCopy = registrationUiState.value.copy(email = email, error = uiError)
            registrationUiState.value = stateCopy as AuthUiState.RegistrationUiState
        } else {
            stateCopy = registrationUiState.value.copy(email = email, error = uiError)
            registrationUiState.value = stateCopy as AuthUiState.RegistrationUiState
        }
    }

    fun changeRegistrationContact(contact: String) {
        uiError = InputValidator.TextFieldValidator.validate(contact, null)
        if (uiError != null) {
            stateCopy = registrationUiState.value.copy(contact = contact, error = uiError)
            registrationUiState.value = stateCopy as AuthUiState.RegistrationUiState
        } else {
            stateCopy = registrationUiState.value.copy(contact = contact, error = uiError)
            registrationUiState.value = stateCopy as AuthUiState.RegistrationUiState
        }
    }

    fun changeRegistrationPassword(password: String) {
        uiError = InputValidator.PasswordValidator.validate(password, 7)
        if (uiError != null) {
            stateCopy = registrationUiState.value.copy(password = password, error = uiError)
            registrationUiState.value = stateCopy as AuthUiState.RegistrationUiState
        } else {
            stateCopy = registrationUiState.value.copy(password = password, error = uiError)
            registrationUiState.value = stateCopy as AuthUiState.RegistrationUiState
        }
    }

    fun changeUserType(userType: UserType) {
        val copy = registrationUiState.value.copy(userType = userType)
        registrationUiState.value = copy
    }

    fun changeLoginEmail(email: String) {
        uiError = InputValidator.EmailValidator.validate(email, null)
        if (uiError != null) {
            stateCopy = loginUiState.value.copy(email = email, error = uiError)
            loginUiState.value = stateCopy as AuthUiState.LoginUiState
        } else {
            stateCopy = loginUiState.value.copy(email = email, error = uiError)
            loginUiState.value = stateCopy as AuthUiState.LoginUiState
        }
    }

    fun changeLoginPassword(password: String) {
        uiError = InputValidator.PasswordValidator.validate(password, 7)
        if (uiError != null) {
            stateCopy = loginUiState.value.copy(password = password, error = uiError)
            loginUiState.value = stateCopy as AuthUiState.LoginUiState
        } else {
            stateCopy = loginUiState.value.copy(password = password, error = uiError)
            loginUiState.value = stateCopy as AuthUiState.LoginUiState
        }
    }

    fun emailPasswordLogin() {

    }

    fun registerUser() {

    }


}

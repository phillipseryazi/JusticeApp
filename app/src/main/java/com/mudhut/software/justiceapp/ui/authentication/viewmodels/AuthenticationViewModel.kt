package com.mudhut.software.justiceapp.ui.authentication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.software.justiceapp.domain.usecases.auth.EmailPasswordLoginUseCase
import com.mudhut.software.justiceapp.domain.usecases.auth.EmailPasswordRegistrationUseCase
import com.mudhut.software.justiceapp.utils.InputValidator
import com.mudhut.software.justiceapp.utils.Resource
import com.mudhut.software.justiceapp.utils.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AuthUiState {
    var email: String
    var password: String
    var loading: Boolean
    var isAuthorised: Boolean
    var validationError: String?
    var toastError: String?
    var focusedTextField: FocusedTextField?

    data class RegistrationUiState(
        override var email: String = "",
        override var password: String = "",
        override var loading: Boolean = false,
        override var isAuthorised: Boolean = false,
        override var validationError: String? = null,
        override var toastError: String? = null,
        override var focusedTextField: FocusedTextField? = null,
        var username: String = "",
        var contact: String = "",
        var userType: UserType? = null,
    ) : AuthUiState

    data class LoginUiState(
        override var email: String = "",
        override var password: String = "",
        override var loading: Boolean = false,
        override var isAuthorised: Boolean = false,
        override var validationError: String? = null,
        override var toastError: String? = null,
        override var focusedTextField: FocusedTextField? = null,
    ) : AuthUiState
}

enum class FocusedTextField(val value: String) {
    USERNAME("Username"),
    EMAIL("Email"),
    CONTACT("Contact"),
    PASSWORD("Password")
}

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val emailPasswordRegistrationUseCase: EmailPasswordRegistrationUseCase,
    private val emailPasswordLoginUseCase: EmailPasswordLoginUseCase,
) : ViewModel() {
    var uiError: String? = null

    var registrationUiState = MutableStateFlow(AuthUiState.RegistrationUiState())
        private set

    var loginUiState = MutableStateFlow(AuthUiState.LoginUiState())
        private set

    fun changeRegistrationUsername(username: String) {
        uiError = InputValidator.TextFieldValidator.validate(username, null)
        registrationUiState.value = registrationUiState.value.copy(
            username = username,
            validationError = uiError
        )
    }

    fun changeRegistrationEmail(email: String) {
        uiError = InputValidator.EmailValidator.validate(email, null)
        registrationUiState.value = registrationUiState.value.copy(
            email = email,
            validationError = uiError
        )
    }

    fun changeRegistrationContact(contact: String) {
        uiError = InputValidator.TextFieldValidator.validate(contact, null)
        registrationUiState.value = registrationUiState.value.copy(
            contact = contact,
            validationError = uiError
        )
    }

    fun changeRegistrationPassword(password: String) {
        uiError = InputValidator.PasswordValidator.validate(password, 7)
        registrationUiState.value = registrationUiState.value.copy(
            password = password,
            validationError = uiError
        )
    }

    fun changeUserType(userType: UserType) {
        registrationUiState.value = registrationUiState.value.copy(
            userType = userType
        )
    }

    fun changeLoginEmail(email: String) {
        uiError = InputValidator.EmailValidator.validate(email, null)
        loginUiState.value = loginUiState.value.copy(
            email = email,
            validationError = uiError
        )
    }

    fun changeLoginPassword(password: String) {
        uiError = InputValidator.PasswordValidator.validate(password, 7)
        loginUiState.value = loginUiState.value.copy(
            password = password,
            validationError = uiError
        )
    }

    fun setRegistrationFocusedTextField(textField: FocusedTextField) {
        registrationUiState.value = registrationUiState.value.copy(
            focusedTextField = textField
        )
    }

    fun setLoginFocusedTextField(textField: FocusedTextField) {
        loginUiState.value = loginUiState.value.copy(
            focusedTextField = textField
        )
    }

    fun emailPasswordLogin() {
        if (loginUiState.value.email.isEmpty() || loginUiState.value.password.isEmpty()) {
            loginUiState.value = loginUiState.value.copy(
                toastError = "Please provide all required information"
            )
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                emailPasswordRegistrationUseCase.invoke(
                    loginUiState.value.email,
                    loginUiState.value.password
                ).collect {
                    when (it) {
                        is Resource.Loading -> {
                            loginUiState.value = loginUiState.value.copy(
                                loading = true
                            )
                        }
                        is Resource.Success -> {
                            loginUiState.value = loginUiState.value.copy(
                                loading = it.data ?: false,
                                isAuthorised = true
                            )
                        }
                        is Resource.Error -> {
                            loginUiState.value = loginUiState.value.copy(
                                loading = it.data ?: false,
                                toastError = it.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun emailPasswordRegistration() {
        val user = registrationUiState.value
        if (user.username.isEmpty() || user.email.isEmpty() || user.contact.isEmpty() || user.password.isEmpty() || user.userType == null) {
            registrationUiState.value = registrationUiState.value.copy(
                toastError = "Please provide all required information"
            )
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                emailPasswordLoginUseCase.invoke(
                    registrationUiState.value.email,
                    registrationUiState.value.password
                ).collect {
                    when (it) {
                        is Resource.Loading -> {
                            registrationUiState.value = registrationUiState.value.copy(
                                loading = true
                            )
                        }
                        is Resource.Success -> {
                            loginUiState.value = loginUiState.value.copy(
                                loading = it.data ?: false,
                                isAuthorised = true
                            )
                        }
                        is Resource.Error -> {
                            registrationUiState.value = registrationUiState.value.copy(
                                loading = it.data ?: false,
                                toastError = it.message
                            )
                        }
                    }
                }
            }
        }
    }
}

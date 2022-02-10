package com.mudhut.software.justiceapp.ui.authentication.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mudhut.software.justiceapp.data.datastore.ProfileDatastoreManager
import com.mudhut.software.justiceapp.domain.usecases.auth.EmailPasswordLoginUseCase
import com.mudhut.software.justiceapp.domain.usecases.auth.EmailPasswordRegistrationUseCase
import com.mudhut.software.justiceapp.domain.usecases.profiles.CreateUserProfileUseCase
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
    var isLoading: Boolean
    var isAuthorised: Boolean
    var hasError: Boolean
    var error: FormError?
    var focusedTextField: FocusedTextField?

    data class RegistrationUiState(
        override var email: String = "",
        override var password: String = "",
        override var isLoading: Boolean = false,
        override var isAuthorised: Boolean = false,
        override var hasError: Boolean = false,
        override var error: FormError? = null,
        override var focusedTextField: FocusedTextField? = null,
        var username: String = "",
        var contact: String = "",
        var userType: UserType? = null,
    ) : AuthUiState

    data class LoginUiState(
        override var email: String = "",
        override var password: String = "",
        override var isLoading: Boolean = false,
        override var isAuthorised: Boolean = false,
        override var hasError: Boolean = false,
        override var error: FormError? = null,
        override var focusedTextField: FocusedTextField? = null,
    ) : AuthUiState
}

data class FormError(
    val message: String,
    val type: FormErrorType
)

enum class FocusedTextField(val value: String) {
    USERNAME("Username"),
    EMAIL("Email"),
    CONTACT("Contact"),
    PASSWORD("Password")
}

enum class FormErrorType(val value: String) {
    VALIDATION("Validation"),
    TOAST("Toast")
}

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val datastore: ProfileDatastoreManager,
    private val emailPasswordRegistrationUseCase: EmailPasswordRegistrationUseCase,
    private val emailPasswordLoginUseCase: EmailPasswordLoginUseCase,
    private val createUserProfileUseCase: CreateUserProfileUseCase
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
            hasError = uiError != null,
            error = if (uiError != null) {
                createError(
                    message = uiError ?: "Unknown error",
                    type = FormErrorType.VALIDATION
                )
            } else {
                null
            }
        )
    }

    fun changeRegistrationEmail(email: String) {
        uiError = InputValidator.EmailValidator.validate(email, null)
        registrationUiState.value = registrationUiState.value.copy(
            email = email,
            hasError = uiError != null,
            error = if (uiError != null) {
                createError(
                    message = uiError ?: "Unknown error",
                    type = FormErrorType.VALIDATION
                )
            } else {
                null
            }
        )
    }

    fun changeRegistrationContact(contact: String) {
        uiError = InputValidator.TextFieldValidator.validate(contact, null)
        registrationUiState.value = registrationUiState.value.copy(
            contact = contact,
            hasError = uiError != null,
            error = if (uiError != null) {
                createError(
                    message = uiError ?: "Unknown error",
                    type = FormErrorType.VALIDATION
                )
            } else {
                null
            }
        )
    }

    fun changeRegistrationPassword(password: String) {
        uiError = InputValidator.PasswordValidator.validate(password, 7)
        registrationUiState.value = registrationUiState.value.copy(
            password = password,
            hasError = uiError != null,
            error = if (uiError != null) {
                createError(
                    message = uiError ?: "Unknown error",
                    type = FormErrorType.VALIDATION
                )
            } else {
                null
            }
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
            hasError = uiError != null,
            error = if (uiError != null) {
                createError(
                    message = uiError ?: "Unknown error",
                    type = FormErrorType.VALIDATION
                )
            } else {
                null
            }
        )
    }

    fun changeLoginPassword(password: String) {
        uiError = InputValidator.PasswordValidator.validate(password, 7)
        loginUiState.value = loginUiState.value.copy(
            password = password,
            hasError = uiError != null,
            error = if (uiError != null) {
                createError(
                    message = uiError ?: "Unknown error",
                    type = FormErrorType.VALIDATION
                )
            } else {
                null
            }
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

    private fun createError(message: String, type: FormErrorType): FormError {
        return FormError(message, type)
    }

    fun resetErrorLogin() {
        loginUiState.value = loginUiState.value.copy(
            hasError = false
        )
    }

    fun resetErrorRegistration() {
        registrationUiState.value = registrationUiState.value.copy(
            hasError = false
        )
    }

    fun emailPasswordLogin() {
        if (loginUiState.value.email.isEmpty() || loginUiState.value.password.isEmpty()) {
            loginUiState.value = loginUiState.value.copy(
                hasError = true,
                error = createError(
                    message = "Please provide all required information",
                    type = FormErrorType.TOAST
                )
            )
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                emailPasswordLoginUseCase.invoke(
                    loginUiState.value.email,
                    loginUiState.value.password
                ).collect {
                    when (it) {
                        is Resource.Loading -> {
                            loginUiState.value = loginUiState.value.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            loginUiState.value = loginUiState.value.copy(
                                isLoading = it.data ?: false,
                                isAuthorised = true
                            )
                        }
                        is Resource.Error -> {
                            loginUiState.value = loginUiState.value.copy(
                                isLoading = it.data ?: false,
                                hasError = true,
                                error = createError(
                                    message = it.message ?: "Unknown error",
                                    type = FormErrorType.TOAST
                                )
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
                hasError = true,
                error = createError(
                    message = "Please provide all required information",
                    type = FormErrorType.TOAST
                )
            )
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                emailPasswordRegistrationUseCase.invoke(
                    registrationUiState.value.email,
                    registrationUiState.value.password
                ).collect {
                    when (it) {
                        is Resource.Loading -> {
                            registrationUiState.value = registrationUiState.value.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            registrationUiState.value = registrationUiState.value.copy(
                                isLoading = it.data ?: false,
                                isAuthorised = true
                            )
                            createUserProfile()
                        }
                        is Resource.Error -> {
                            registrationUiState.value = registrationUiState.value.copy(
                                isLoading = it.data ?: false,
                                error = createError(
                                    message = it.message ?: "Unknown error",
                                    type = FormErrorType.TOAST
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun createUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            createUserProfileUseCase.invoke(
                registrationUiState.value.username,
                registrationUiState.value.email,
                registrationUiState.value.contact,
                registrationUiState.value.userType?.label ?: "",
                ""
            ).collect {
                when (it) {
                    is Resource.Loading -> {
                        Log.i("UserProfile", "Creating user profile")
                    }
                    is Resource.Success -> {
                        datastore.writeToDataStore("Username", it.data?.username ?: "")
                        datastore.writeToDataStore("Email", it.data?.email ?: "")
                        datastore.writeToDataStore("Contact", it.data?.contact ?: "")
                        datastore.writeToDataStore("UserType", it.data?.userType ?: "")
                        datastore.writeToDataStore("Avatar", it.data?.avatar ?: "")
                    }
                    is Resource.Error -> {
                        Log.e("UserProfile", it.message ?: "Unknown error")
                    }
                }
            }
        }
    }
}

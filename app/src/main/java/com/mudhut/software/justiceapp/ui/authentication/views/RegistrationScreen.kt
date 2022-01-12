package com.mudhut.software.justiceapp.ui.authentication.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.AuthUiState
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.FocusedTextField
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.FormErrorType
import com.mudhut.software.justiceapp.ui.common.ErrorBanner
import com.mudhut.software.justiceapp.ui.common.LoadingPage
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.mudhut.software.justiceapp.utils.UserType
import com.mudhut.software.justiceapp.utils.getUserTypes

@Composable
fun RegistrationScreen(
    navigateToHome: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onContactChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onUserTypeChange: (UserType) -> Unit,
    onFocusedTextField: (FocusedTextField) -> Unit,
    onRegistrationPressed: () -> Unit,
    resetError: () -> Unit,
    uiState: AuthUiState.RegistrationUiState
) {
    val scaffoldState = rememberScaffoldState()

    if (uiState.hasError) {
        uiState.error?.let {
            if (it.type == FormErrorType.TOAST) {
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiState.error!!.message,
                        duration = SnackbarDuration.Short
                    )
                    resetError()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        scaffoldState = scaffoldState,
    ) {
        if (uiState.isLoading) {
            LoadingPage()
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                RegistrationTitleSection()
                Spacer(modifier = Modifier.height(24.dp))
                RegistrationSection(
                    onUsernameChange = onUsernameChange,
                    onEmailChange = onEmailChange,
                    onContactChange = onContactChange,
                    onPasswordChange = onPasswordChange,
                    onUserTypeChange = onUserTypeChange,
                    onRegistrationPressed = onRegistrationPressed,
                    onFocusedTextField = onFocusedTextField,
                    uiState = uiState
                )
            }
        }
    }
}

@Composable
fun RegistrationTitleSection() {
    Text(
        stringResource(R.string.register),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.h1,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RegistrationSection(
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onContactChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onUserTypeChange: (UserType) -> Unit,
    onRegistrationPressed: () -> Unit,
    onFocusedTextField: (FocusedTextField) -> Unit,
    uiState: AuthUiState.RegistrationUiState
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.width(270.dp)) {
        RegistrationTextField(
            modifier = Modifier
                .width(270.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        onFocusedTextField(FocusedTextField.USERNAME)
                    }
                },
            value = uiState.username,
            label = stringResource(R.string.username),
            uiState = uiState,
            onInputChange = onUsernameChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        RegistrationTextField(
            modifier = Modifier
                .width(270.dp)
                .width(270.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        onFocusedTextField(FocusedTextField.EMAIL)
                    }
                },
            value = uiState.email,
            label = stringResource(R.string.email),
            uiState = uiState,
            onInputChange = onEmailChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        RegistrationTextField(
            modifier = Modifier
                .width(270.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        onFocusedTextField(FocusedTextField.CONTACT)
                    }
                },
            value = uiState.contact,
            label = stringResource(R.string.contact),
            uiState = uiState,
            onInputChange = onContactChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        RegistrationTextField(
            modifier = Modifier
                .width(270.dp)
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        onFocusedTextField(FocusedTextField.PASSWORD)
                    }
                },
            value = uiState.password,
            label = stringResource(R.string.password),
            uiState = uiState,
            onInputChange = onPasswordChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserTypeChipGroup(
            selectedType = uiState.userType,
            onSelectionChange = onUserTypeChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        RegistrationButton(onRegistrationPressed)
    }
}

@Composable
fun RegistrationTextField(
    modifier: Modifier,
    value: String,
    label: String,
    uiState: AuthUiState.RegistrationUiState,
    onInputChange: (String) -> Unit
) {
    Column {
        TextField(
            modifier = modifier,
            value = value,
            label = { Text(label) },
            onValueChange = onInputChange,
            isError = if (uiState.focusedTextField?.value == label) {
                uiState.hasError
            } else {
                false
            }
        )
        uiState.error?.let {
            if (it.type == FormErrorType.VALIDATION && uiState.focusedTextField?.value == label) {
                ErrorBanner(string = it.message)
            }
        }
    }
}

@Composable
fun UserTypeChip(
    userType: UserType,
    isSelected: Boolean = false,
    onSelection: (UserType) -> Unit = {}
) {
    Surface(
        modifier = Modifier.padding(2.dp),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colors.primary else Color.LightGray
    ) {
        Row(modifier = Modifier.toggleable(
            value = false,
            onValueChange = {
                onSelection(userType)
            }
        )) {
            Text(
                text = userType.label,
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    end = 8.dp,
                    start = 8.dp
                ),
                style = MaterialTheme.typography.body1,
                color = if (isSelected) Color.White else Color.Black,
            )
        }
    }
}

@Composable
fun UserTypeChipGroup(
    userTypes: List<UserType> = getUserTypes(),
    selectedType: UserType? = null,
    onSelectionChange: (UserType) -> Unit = {}
) {
    FlowRow(modifier = Modifier.fillMaxWidth()) {
        userTypes.forEach {
            UserTypeChip(
                userType = it,
                isSelected = selectedType == it,
                onSelection = { userType ->
                    onSelectionChange(userType)
                }
            )
        }
    }
}

@Composable
fun RegistrationButton(onRegistrationPressed: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            contentPadding = PaddingValues(0.dp),
            onClick = onRegistrationPressed,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(
                stringResource(R.string.register),
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview
@Composable
fun RegistrationScreenPreview() {
    JusticeAppTheme {
        RegistrationScreen(
            navigateToHome = {},
            onUsernameChange = {},
            onEmailChange = {},
            onContactChange = {},
            onPasswordChange = {},
            onUserTypeChange = {},
            onFocusedTextField = {},
            onRegistrationPressed = {},
            resetError = {},
            uiState = AuthUiState.RegistrationUiState()
        )
    }
}

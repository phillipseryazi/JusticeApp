package com.mudhut.software.justiceapp.ui.authentication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.AuthUiState
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.FocusedTextField
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.FormErrorType
import com.mudhut.software.justiceapp.ui.common.ErrorBanner
import com.mudhut.software.justiceapp.ui.common.LoadingPage
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme

@Composable
fun LoginScreen(
    navigateToRegistration: () -> Unit,
    navigateToHome: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginPressed: () -> Unit,
    onFocusedTextField: (FocusedTextField) -> Unit,
    resetError: () -> Unit,
    uiState: AuthUiState.LoginUiState
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

    if (uiState.isAuthorised) {
        LaunchedEffect(uiState.isAuthorised) {
            navigateToHome()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        if (uiState.isLoading) {
            LoadingPage()
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                LoginTitleSection()
                Spacer(modifier = Modifier.height(24.dp))
                LoginSection(
                    navigateToHome,
                    navigateToRegistration,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onLoginPressed = onLoginPressed,
                    onFocusedTextField = onFocusedTextField,
                    uiState
                )
                Spacer(modifier = Modifier.height(24.dp))
                OrSection()
                Spacer(modifier = Modifier.height(24.dp))
                SocialLoginSection()
            }
        }
    }
}

@Composable
fun LoginTitleSection() {
    Text(
        stringResource(R.string.login),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.h1,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LoginSection(
    navigateToHome: () -> Unit,
    navigateToRegistration: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginPressed: () -> Unit,
    onFocusedTextField: (FocusedTextField) -> Unit,
    uiState: AuthUiState.LoginUiState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginTextField(
            modifier = Modifier
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
        LoginTextField(
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
        Button(
            contentPadding = PaddingValues(0.dp),
            onClick = onLoginPressed,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(
                stringResource(R.string.login),
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.go_to_registration),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    navigateToRegistration()
                },
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun LoginTextField(
    modifier: Modifier,
    value: String,
    label: String,
    uiState: AuthUiState.LoginUiState,
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
fun OrSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_horizontal_rule
            ),
            contentDescription = null,
            modifier = Modifier.size(
                width = 40.dp,
                height = 20.dp
            )
        )
        Text(
            stringResource(R.string.text_or),
            style = MaterialTheme.typography.body1
        )
        Image(
            painter = painterResource(
                id = R.drawable.ic_horizontal_rule
            ),
            contentDescription = null,
            modifier = Modifier.size(width = 40.dp, height = 20.dp)
        )
    }
}

@Composable
fun SocialLoginSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            contentPadding = PaddingValues(0.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ),
            modifier = Modifier
                .width(270.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_google),
                contentDescription = stringResource(R.string.google),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.continue_with_google),
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 8.dp
                ),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            contentPadding = PaddingValues(0.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                backgroundColor = Color.White
            ),
            modifier = Modifier
                .width(270.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_icon_anonymous
                ),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(R.string.continue_anonymously),
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 8.dp
                ),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    JusticeAppTheme {
        LoginScreen(
            navigateToRegistration = {},
            navigateToHome = {},
            onEmailChange = {},
            onPasswordChange = {},
            onLoginPressed = {},
            onFocusedTextField = {},
            resetError = {},
            uiState = AuthUiState.LoginUiState()
        )
    }
}

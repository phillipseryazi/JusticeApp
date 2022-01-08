package com.mudhut.software.justiceapp.ui.authentication.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.flowlayout.FlowRow
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.AuthUiState
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.AuthenticationViewModel
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.mudhut.software.justiceapp.utils.UserType
import com.mudhut.software.justiceapp.utils.getUserTypes

@Composable
fun RegistrationScreen(
    viewModel: AuthenticationViewModel = viewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val uiState by viewModel.registrationUiState

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        scaffoldState = scaffoldState,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            RegistrationTitleSection()
            Spacer(modifier = Modifier.height(24.dp))
            RegistrationSection(
                onUsernameChange = {
                    viewModel.changeRegistrationUsername(it)
                },
                onEmailChange = {
                    viewModel.changeRegistrationEmail(it)
                },
                onContactChange = {
                    viewModel.changeRegistrationContact(it)
                },
                onPasswordChange = {
                    viewModel.changeRegistrationPassword(it)
                },
                onUserTypeChange = {
                    viewModel.changeUserType(it)
                },
                onUserRegistration = {
                    viewModel.registerUser()
                },
                uiState = uiState
            )
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
    onUserRegistration: () -> Unit,
    uiState: AuthUiState.RegistrationUiState
) {
    Column(modifier = Modifier.width(270.dp)) {
        TextField(
            modifier = Modifier.width(270.dp),
            value = uiState.username,
            label = { Text(stringResource(R.string.username)) },
            onValueChange = onUsernameChange,
            isError = uiState.error != null
        )
        uiState.error?.let { ErrorBanner(string = it) }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.width(270.dp),
            value = uiState.email,
            label = { Text(stringResource(R.string.email)) },
            onValueChange = onEmailChange,
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.width(270.dp),
            value = uiState.contact,
            label = { Text(stringResource(R.string.contact)) },
            onValueChange = onContactChange,
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.width(270.dp),
            value = uiState.password,
            label = { Text(stringResource(R.string.password)) },
            onValueChange = onPasswordChange,
        )
        Spacer(modifier = Modifier.height(16.dp))
        UserTypeChipGroup(
            selectedType = uiState.userType,
            onSelectionChange = onUserTypeChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        RegistrationButton(onUserRegistration)
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
fun RegistrationButton(onUserRegistration: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            contentPadding = PaddingValues(0.dp),
            onClick = onUserRegistration,
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

@Composable
fun ErrorBanner(string: String) {
    Text(string, color = MaterialTheme.colors.error, fontSize = 12.sp)
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    JusticeAppTheme {
        RegistrationScreen(viewModel = AuthenticationViewModel())
    }
}

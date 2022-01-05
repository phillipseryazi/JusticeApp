package com.mudhut.software.justiceapp.authentication.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.authentication.viewmodels.AuthenticationViewModel
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme

@Composable
fun LoginScreen(
    navigateToRegistration: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: AuthenticationViewModel = viewModel()
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            LoginTitleSection()
            Spacer(modifier = Modifier.height(24.dp))
            LoginSection(
                navigateToHome,
                navigateToRegistration,
                viewModel = viewModel
            )
            Spacer(modifier = Modifier.height(24.dp))
            OrSection()
            Spacer(modifier = Modifier.height(24.dp))
            SocialLoginSection()
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
    viewModel: AuthenticationViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.width(270.dp),
            value = "",
            label = { Text(stringResource(R.string.username)) },
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.width(270.dp),
            value = "",
            label = { Text(stringResource(R.string.password)) },
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            contentPadding = PaddingValues(0.dp),
            onClick = {
                navigateToHome()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(
                stringResource(R.string.login),
                modifier = Modifier.padding(
                    top = 0.dp,
                    bottom = 0.dp,
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
            contentPadding = PaddingValues(12.dp),
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
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            contentPadding = PaddingValues(12.dp),
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
            viewModel = AuthenticationViewModel()
        )
    }
}

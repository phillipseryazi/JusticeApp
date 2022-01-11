package com.mudhut.software.justiceapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.AuthenticationViewModel
import com.mudhut.software.justiceapp.ui.authentication.views.LoginScreen
import com.mudhut.software.justiceapp.ui.authentication.views.RegistrationScreen
import com.mudhut.software.justiceapp.ui.onboarding.views.OnBoardingScreen

@ExperimentalPagerApi
@Composable
fun NavigationComposable() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.OnBoardingScreen.route,
        builder = {
            composable(Destination.OnBoardingScreen.route) {
                OnBoardingScreen(
                    navigateToLogin = {
                        navController.navigate(Destination.LoginScreen.route)
                    })
            }

            composable(Destination.LoginScreen.route) {
                val viewModel = hiltViewModel<AuthenticationViewModel>()
                LoginScreen(
                    navigateToRegistration = {
                        navController.navigate(Destination.RegistrationScreen.route)
                    },
                    navigateToHome = {
                        navController.navigate(Destination.HomeScreen.route)
                    },
                    onEmailChange = {
                        viewModel.changeLoginEmail(it)
                    },
                    onPasswordChange = {
                        viewModel.changeLoginPassword(it)
                    },
                    onLoginPressed = {
                        viewModel.emailPasswordLogin()
                    },
                    onFocusedTextField = {
                        viewModel.setLoginFocusedTextField(it)
                    },
                    uiState = viewModel.loginUiState.collectAsState().value
                )
            }

            composable(Destination.RegistrationScreen.route) {
                val viewModel = hiltViewModel<AuthenticationViewModel>()
                RegistrationScreen(
                    navigateToHome = {
                        navController.navigate(Destination.HomeScreen.route)
                    },
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
                    onFocusedTextField = {
                        viewModel.setRegistrationFocusedTextField(it)
                    },
                    onRegistrationPressed = {
                        viewModel.emailPasswordRegistration()
                    },
                    uiState = viewModel.registrationUiState.collectAsState().value
                )
            }
        }
    )
}

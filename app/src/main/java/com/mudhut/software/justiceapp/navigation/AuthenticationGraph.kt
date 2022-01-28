package com.mudhut.software.justiceapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mudhut.software.justiceapp.ui.authentication.viewmodels.AuthenticationViewModel
import com.mudhut.software.justiceapp.ui.authentication.views.LoginScreen
import com.mudhut.software.justiceapp.ui.authentication.views.RegistrationScreen
import com.mudhut.software.justiceapp.ui.dashboard.views.DashboardScreen
import com.mudhut.software.justiceapp.ui.onboarding.views.OnBoardingScreen

@ExperimentalPagerApi
@ExperimentalPermissionsApi
fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        startDestination = Destination.OnBoardingScreen.route,
        route = "auth_graph",
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
                    navigateToDashboard = {
                        navController.navigate(Destination.DashboardScreen.route)
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
                    resetError = {
                        viewModel.resetErrorLogin()
                    },
                    uiState = viewModel.loginUiState.collectAsState().value
                )
            }

            composable(Destination.RegistrationScreen.route) {
                val viewModel = hiltViewModel<AuthenticationViewModel>()
                RegistrationScreen(
                    navigateToDashboard = {
                        navController.navigate(Destination.DashboardScreen.route)
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
                    resetError = {
                        viewModel.resetErrorRegistration()
                    },
                    uiState = viewModel.registrationUiState.collectAsState().value
                )
            }

            composable(Destination.DashboardScreen.route) {
                DashboardScreen()
            }
        }
    )
}

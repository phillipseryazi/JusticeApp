package com.mudhut.software.justiceapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mudhut.software.justiceapp.authentication.views.LoginScreen
import com.mudhut.software.justiceapp.authentication.views.RegistrationScreen
import com.mudhut.software.justiceapp.onboarding.views.OnBoardingScreen

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
                LoginScreen(
                    navigateToRegistration = {
                        navController.navigate(Destination.RegistrationScreen.route)
                    },
                    navigateToHome = {
                        navController.navigate(Destination.HomeScreen.route)
                    })
            }

            composable(Destination.RegistrationScreen.route) {
                RegistrationScreen()
            }
        }
    )
}
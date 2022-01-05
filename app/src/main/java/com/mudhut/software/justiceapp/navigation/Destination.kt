package com.mudhut.software.justiceapp.navigation

sealed class Destination(val route: String) {
    object LoginScreen : Destination(LOGIN_SCREEN)
    object OnBoardingScreen : Destination(ONBOARDING_SCREEN)
    object RegistrationScreen : Destination(REGISTRATION_SCREEN)
    object HomeScreen : Destination(HOME_SCREEN)

    companion object {
        private const val ONBOARDING_SCREEN = "on_boarding_screen"
        private const val LOGIN_SCREEN = "login_screen"
        private const val REGISTRATION_SCREEN = "registration_screen"
        private const val HOME_SCREEN = "home_screen"
    }
}

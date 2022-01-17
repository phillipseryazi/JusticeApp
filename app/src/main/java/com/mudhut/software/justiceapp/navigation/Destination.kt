package com.mudhut.software.justiceapp.navigation

sealed class Destination(val route: String) {
    object LoginScreen : Destination(LOGIN_SCREEN)
    object OnBoardingScreen : Destination(ONBOARDING_SCREEN)
    object RegistrationScreen : Destination(REGISTRATION_SCREEN)
    object DashboardScreen : Destination(DASHBOARD_SCREEN)

    object HomeScreen : Destination(HOME_SCREEN)
    object NotificationScreen : Destination(NOTIFICATION_SCREEN)
    object ProfileScreen : Destination(PROFILE_SCREEN)

    companion object {
        // Auth routes
        private const val ONBOARDING_SCREEN = "on_boarding_screen"
        private const val LOGIN_SCREEN = "login_screen"
        private const val REGISTRATION_SCREEN = "registration_screen"
        private const val DASHBOARD_SCREEN = "dashboard_screen"

        // BottomBar routes
        private const val HOME_SCREEN = "home_screen"
        private const val NOTIFICATION_SCREEN = "notification_screen"
        private const val PROFILE_SCREEN = "profile_screen"

    }
}

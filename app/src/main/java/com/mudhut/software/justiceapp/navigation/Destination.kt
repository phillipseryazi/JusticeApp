package com.mudhut.software.justiceapp.navigation

enum class DestinationType {
    DASHBOARD
}

sealed class Destination(val route: String, val type: DestinationType? = null) {
    object LoginScreen : Destination(LOGIN_SCREEN)
    object OnBoardingScreen : Destination(ONBOARDING_SCREEN)
    object RegistrationScreen : Destination(REGISTRATION_SCREEN)
    object DashboardScreen : Destination(DASHBOARD_SCREEN)

    object HomeScreen : Destination(HOME_SCREEN, DestinationType.DASHBOARD)
    object ExploreScreen : Destination(EXPLORE_SCREEN, DestinationType.DASHBOARD)
    object CreateScreen : Destination(CREATE_SCREEN, DestinationType.DASHBOARD)
    object InboxScreen : Destination(NOTIFICATION_SCREEN, DestinationType.DASHBOARD)
    object SettingsScreen : Destination(PROFILE_SCREEN, DestinationType.DASHBOARD)


    object CameraScreen : Destination(CAMERA_SCREEN)

    companion object {
        // Auth routes
        private const val ONBOARDING_SCREEN = "on_boarding_screen"
        private const val LOGIN_SCREEN = "login_screen"
        private const val REGISTRATION_SCREEN = "registration_screen"
        private const val DASHBOARD_SCREEN = "dashboard_screen"

        // BottomBar routes
        private const val HOME_SCREEN = "home_screen"
        private const val EXPLORE_SCREEN = "explore_screen"
        private const val CREATE_SCREEN = "create_screen"
        private const val NOTIFICATION_SCREEN = "notification_screen"
        private const val PROFILE_SCREEN = "profile_screen"

        // Camera route
        private const val CAMERA_SCREEN = "camera_screen"

    }
}

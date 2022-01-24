package com.mudhut.software.justiceapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mudhut.software.justiceapp.ui.dashboard.views.HomeScreen
import com.mudhut.software.justiceapp.ui.dashboard.views.NotificationScreen
import com.mudhut.software.justiceapp.ui.dashboard.views.ProfileScreen

@ExperimentalPermissionsApi
fun NavGraphBuilder.bottomBarGraph(navController: NavController) {
    navigation(
        startDestination = Destination.HomeScreen.route,
        route = "bottom_bar_graph",
        builder = {
            composable(Destination.HomeScreen.route) {
                HomeScreen(navigateToCameraScreen = {
                    navController.navigate("home_screen_graph")
                })
            }
            composable(Destination.NotificationScreen.route) {
                NotificationScreen()
            }
            composable(Destination.ProfileScreen.route) {
                ProfileScreen()
            }
            homeScreenGraph(navController)
        }
    )
}

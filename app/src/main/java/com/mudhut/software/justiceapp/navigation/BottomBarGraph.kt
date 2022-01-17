package com.mudhut.software.justiceapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mudhut.software.justiceapp.ui.dashboard.views.DashboardScreen
import com.mudhut.software.justiceapp.ui.dashboard.views.HomeScreen
import com.mudhut.software.justiceapp.ui.dashboard.views.NotificationScreen
import com.mudhut.software.justiceapp.ui.dashboard.views.ProfileScreen

fun NavGraphBuilder.bottomBarGraph(navController: NavController) {
    navigation(
        startDestination = Destination.HomeScreen.route,
        route = "bottom_bar_graph",
        builder = {
            composable(Destination.HomeScreen.route) {
                HomeScreen()
            }
            composable(Destination.NotificationScreen.route) {
                NotificationScreen()
            }
            composable(Destination.ProfileScreen.route) {
                ProfileScreen()
            }
        }
    )
}

package com.mudhut.software.justiceapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mudhut.software.justiceapp.ui.camera.views.CameraScreen

@ExperimentalPermissionsApi
fun NavGraphBuilder.homeScreenGraph(navController: NavController) {
    navigation(
        startDestination = Destination.CameraScreen.route,
        route = "home_screen_graph",
        builder = {
            composable(Destination.CameraScreen.route) {
                CameraScreen()
            }
        }
    )
}

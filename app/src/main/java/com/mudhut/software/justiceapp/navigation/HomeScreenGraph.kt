package com.mudhut.software.justiceapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
fun NavGraphBuilder.homeScreenGraph(navController: NavController) {
    navigation(
        startDestination = Destination.CameraScreen.route,
        route = "home_screen_graph",
        builder = {}
    )
}

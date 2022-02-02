package com.mudhut.software.justiceapp.navigation

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mudhut.software.justiceapp.ui.camera.views.CameraScreen
import com.mudhut.software.justiceapp.ui.dashboard.views.*

@ExperimentalPermissionsApi
fun NavGraphBuilder.bottomBarGraph(navController: NavController) {
    navigation(
        startDestination = Destination.HomeScreen.route,
        route = "bottom_bar_graph",
        builder = {
            composable(Destination.HomeScreen.route) {
                HomeScreen()
            }
            composable(Destination.ExploreScreen.route) {
                ExploreScreen()
            }
            composable(Destination.CreateScreen.route) {
                CreateScreen()
            }
            composable(Destination.InboxScreen.route) {
                InboxScreen()
            }
            composable(Destination.SettingsScreen.route) {
                SettingsScreen()
            }
            composable(Destination.CameraScreen.route) {
                CameraScreen(goToSettings = {
                    navController.context.startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", navController.context.packageName, null)
                        )
                    )
                })
            }
        }
    )
}

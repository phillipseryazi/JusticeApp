package com.mudhut.software.justiceapp.navigation

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mudhut.software.justiceapp.ui.camera.views.CameraScreen
import com.mudhut.software.justiceapp.ui.dashboard.viewmodels.CreateScreenViewModel
import com.mudhut.software.justiceapp.ui.dashboard.viewmodels.HomeScreenViewModel
import com.mudhut.software.justiceapp.ui.dashboard.views.*

@ExperimentalPermissionsApi
fun NavGraphBuilder.bottomBarGraph(navController: NavController) {
    navigation(
        startDestination = Destination.HomeScreen.route,
        route = "bottom_bar_graph",
        builder = {
            composable(Destination.HomeScreen.route) {
                val viewModel = hiltViewModel<HomeScreenViewModel>()
                HomeScreen(
                    onUpVoteClicked = { postId, pos ->
                        viewModel.upVotePost(postId, pos)
                    },
                    viewModel.uiState.collectAsState().value
                )
            }
            composable(Destination.ExploreScreen.route) {
                ExploreScreen()
            }
            composable(Destination.CreateScreen.route) {
                val viewModel = hiltViewModel<CreateScreenViewModel>()
                CreateScreen(
                    addItemToMediaList = { uri ->
                        val strings = uri.map { it.toString() }
                        viewModel.addUriToMediaList(strings)
                    },
                    removeItemFromMediaList = {
                        viewModel.removeUriFromMediaList(it)
                    },
                    onCaptionChange = {
                        viewModel.onCaptionChange(it)
                    },
                    onPopBackStack = {
                        navController.popBackStack()
                    },
                    onPostClick = {
                        viewModel.postItem()
                    },
                    resetMessage = {
                        viewModel.resetMessage()
                    },
                    uiState = viewModel.uiState.collectAsState().value
                )
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

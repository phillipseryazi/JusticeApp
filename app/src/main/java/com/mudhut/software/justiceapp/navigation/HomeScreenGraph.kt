package com.mudhut.software.justiceapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mudhut.software.justiceapp.ui.dashboard.viewmodels.CommentScreenViewModel
import com.mudhut.software.justiceapp.ui.dashboard.viewmodels.HomeScreenViewModel
import com.mudhut.software.justiceapp.ui.dashboard.views.CommentScreen
import com.mudhut.software.justiceapp.ui.dashboard.views.HomeScreen
import com.mudhut.software.justiceapp.utils.HOME_SCREEN_GRAPH

@ExperimentalPermissionsApi
fun NavGraphBuilder.homeScreenGraph(navController: NavController) {
    navigation(
        startDestination = Destination.HomeScreen.route,
        route = HOME_SCREEN_GRAPH,
        builder = {
            composable(Destination.HomeScreen.route) {
                val viewModel = hiltViewModel<HomeScreenViewModel>()
                HomeScreen(
                    upVotePost = { postId, pos ->
                        viewModel.upVotePost(postId, pos)
                    },
                    unVotePost = { postId, pos ->
                        viewModel.unVotePost(postId, pos)
                    },
                    goToComments = { postId ->
                        navController.navigate("${Destination.CommentScreen.route}/$postId")
                    },
                    viewModel.uiState.collectAsState().value
                )
            }

            composable(
                "${Destination.CommentScreen.route}/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.StringType })
            ) { backStackEntry ->
                val viewModel = hiltViewModel<CommentScreenViewModel>()
                CommentScreen(
                    getComments = {
                        backStackEntry.arguments?.getString("postId")?.let { postId ->
                            viewModel.getComments(postId, 0)
                        }
                    },
                    postComment = { postId, content ->
                        viewModel.postComments(postId, content)
                    },
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    )
}

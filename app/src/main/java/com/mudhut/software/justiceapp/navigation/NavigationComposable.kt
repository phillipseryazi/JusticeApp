package com.mudhut.software.justiceapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mudhut.software.justiceapp.ui.dashboard.views.DashboardScreen

@ExperimentalPagerApi
@ExperimentalPermissionsApi
@Composable
fun NavigationComposable() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "auth_graph",
        builder = {
            authGraph(navController)
        }
    )
}

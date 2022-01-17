package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mudhut.software.justiceapp.navigation.Destination
import com.mudhut.software.justiceapp.navigation.bottomBarGraph
import com.mudhut.software.justiceapp.ui.dashboard.models.BottomNavItem
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme

@Composable
fun DashboardScreen() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNavigationBar(
                navItems = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = Destination.HomeScreen.route,
                        icon = Icons.Filled.Home
                    ),
                    BottomNavItem(
                        name = "Notifications",
                        route = Destination.NotificationScreen.route,
                        icon = Icons.Filled.Email
                    ),
                    BottomNavItem(
                        name = "Profile",
                        route = Destination.ProfileScreen.route,
                        icon = Icons.Filled.Person
                    )
                ),
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, end = 0.dp)
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "bottom_bar_graph",
            builder = {
                bottomBarGraph(navController)
            }
        )
    }
}

@Composable
fun BottomNavigationBar(
    navItems: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        elevation = 2.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route
        navItems.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination == screen.route,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Black,
                icon = { Icon(screen.icon, contentDescription = null) },
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    JusticeAppTheme() {
        DashboardScreen()
    }
}

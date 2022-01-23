package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.navigation.Destination
import com.mudhut.software.justiceapp.navigation.bottomBarGraph
import com.mudhut.software.justiceapp.ui.dashboard.models.BottomNavItem
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme

@ExperimentalPermissionsApi
@Composable
fun DashboardScreen() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.primarySurface,
                cutoutShape = CircleShape,
                elevation = 2.dp
            ) {
                BottomNavigationBar(
                    navItems = listOf(
                        BottomNavItem(
                            name = "Home",
                            route = Destination.HomeScreen.route,
                            icon = Icons.Filled.Home
                        ),
                        BottomNavItem(
                            name = "Inbox",
                            route = Destination.NotificationScreen.route,
                            icon = Icons.Filled.Email
                        ),
                        BottomNavItem(
                            name = "Profile",
                            route = Destination.ProfileScreen.route,
                            icon = Icons.Filled.Person
                        ),
                        BottomNavItem(
                            name = "",
                            route = "",
                            icon = null
                        )
                    ),
                    navController = navController,
                    modifier = Modifier
                        .padding(
                            start = 0.dp,
                            end = 0.dp
                        )
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                contentColor = Color.White,
                onClick = { })
            {
                Icon(
                    painter = painterResource(R.drawable.ic_camera),
                    contentDescription = null
                )
            }
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
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route
        navItems.forEach { screen ->
            BottomNavigationItem(
                label = { Text(screen.name) },
                selected = currentDestination == screen.route,
                selectedContentColor = MaterialTheme.colors.secondaryVariant,
                unselectedContentColor = Color.White,
                icon = { screen.icon?.let { Icon(it, contentDescription = null) } },
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

@ExperimentalPermissionsApi
@Preview
@Composable
fun DashboardScreenPreview() {
    JusticeAppTheme() {
        DashboardScreen()
    }
}

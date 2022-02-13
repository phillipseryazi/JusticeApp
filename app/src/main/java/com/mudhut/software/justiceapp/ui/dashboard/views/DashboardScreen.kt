package com.mudhut.software.justiceapp.ui.dashboard.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
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
import com.mudhut.software.justiceapp.navigation.DestinationType
import com.mudhut.software.justiceapp.navigation.bottomBarGraph
import com.mudhut.software.justiceapp.ui.dashboard.models.BottomNavItem
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme
import com.mudhut.software.justiceapp.utils.getDestinationType

@ExperimentalPermissionsApi
@Composable
fun DashboardScreen() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        bottomBar = {
            if (getDestinationType(currentDestination)?.type == DestinationType.DASHBOARD) {
                BottomNavigationBar(
                    navItems = listOf(
                        BottomNavItem(
                            name = "Home",
                            route = Destination.HomeScreen.route,
                            icon = R.drawable.ic_home
                        ),
                        BottomNavItem(
                            name = "Find",
                            route = Destination.ExploreScreen.route,
                            icon = R.drawable.ic_explore
                        ),
                        BottomNavItem(
                            name = "Post",
                            route = Destination.CreateScreen.route,
                            icon = R.drawable.ic_add
                        ),
                        BottomNavItem(
                            name = "Inbox",
                            route = Destination.InboxScreen.route,
                            icon = R.drawable.ic_email
                        ),
                        BottomNavItem(
                            name = "Settings",
                            route = Destination.SettingsScreen.route,
                            icon = R.drawable.ic_settings
                        )
                    ),
                    navController = navController,
                    currentDestination = currentDestination ?: Destination.HomeScreen.route,
                    modifier = Modifier
                        .padding(
                            start = 0.dp,
                            end = 0.dp
                        ),

                    )
            }
        },
        floatingActionButton = {
            if (getDestinationType(currentDestination)?.type == DestinationType.DASHBOARD) {
                FloatingActionButton(
                    shape = CircleShape,
                    contentColor = Color.White,
                    onClick = {
                        navController.navigate(Destination.CameraScreen.route)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
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
    currentDestination: String,
    modifier: Modifier
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        navItems.forEach { screen ->
            BottomNavigationItem(
                label = { Text(screen.name) },
                selected = currentDestination == screen.route,
                selectedContentColor = MaterialTheme.colors.secondaryVariant,
                unselectedContentColor = Color.White,
                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
                        contentDescription = null
                    )
                },
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

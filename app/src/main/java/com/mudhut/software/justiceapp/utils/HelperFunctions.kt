package com.mudhut.software.justiceapp.utils

import com.mudhut.software.justiceapp.navigation.Destination

enum class UserType(val label: String) {
    CITIZEN("Citizen"),
    OFFICER("Officer"),
    LAWYER("Lawyer"),
    JUDGE("Judge"),
    INVESTIGATOR("Private Investigator"),
    PARALEGAL("Paralegal")
}

fun getUserTypes(): List<UserType> {
    return listOf(
        UserType.CITIZEN,
        UserType.INVESTIGATOR,
        UserType.OFFICER,
        UserType.LAWYER,
        UserType.JUDGE,
        UserType.PARALEGAL
    )
}

fun getDestinationType(currentDestination: String?): Destination? {
    return when (currentDestination) {
        Destination.HomeScreen.route -> Destination.HomeScreen
        Destination.ExploreScreen.route -> Destination.ExploreScreen
        Destination.CreateScreen.route -> Destination.CreateScreen
        Destination.InboxScreen.route -> Destination.InboxScreen
        Destination.SettingsScreen.route -> Destination.SettingsScreen
        else -> null
    }
}

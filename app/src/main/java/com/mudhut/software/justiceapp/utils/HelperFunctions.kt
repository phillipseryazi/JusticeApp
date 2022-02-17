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

fun checkString(string: String): Int {
    val imgRegex = """image""".toRegex()
    val imgExt = """.jpg""".toRegex()

    val vidRegex = """video""".toRegex()
    val vidExt = """.mp4""".toRegex()

    return when {
        imgRegex.containsMatchIn(string) || imgExt.containsMatchIn(string) -> {
            1
        }
        vidRegex.containsMatchIn(string) || vidExt.containsMatchIn(string) -> {
            2
        }
        else -> {
            0
        }
    }
}

fun simplifyCount(num: Int): String {
    return when {
        num in 1000..999999 -> {
            "${(num / 1000)}k"
        }
        num >= 1000000 -> {
            "${(num / 1000000)}M"
        }
        else -> {
            num.toString()
        }
    }
}

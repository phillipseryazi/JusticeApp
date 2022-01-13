package com.mudhut.software.justiceapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

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

fun getUserType(value: String): UserType? {
    val map = UserType.values().associateBy(UserType::label)
    return map[value]
}

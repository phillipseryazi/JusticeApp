package com.mudhut.software.justiceapp.utils

import android.content.Context
import android.content.ContextWrapper
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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

fun getCameraOutputDirectory(context: Context): File {
    val contextWrapper = ContextWrapper(context)
    val directory = contextWrapper.getDir("JusticeApp", Context.MODE_PRIVATE)

    val fileNameFormat = "yyyy-MM-dd-HH-mm-ss-SSS"

    return File(
        directory,
        SimpleDateFormat(
            fileNameFormat,
            Locale.US
        ).format(System.currentTimeMillis())
    ).apply { mkdir() }
}
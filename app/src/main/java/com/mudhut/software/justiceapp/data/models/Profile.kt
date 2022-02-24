package com.mudhut.software.justiceapp.data.models

data class Profile(
    val username: String,
    val uid: String,
    val email: String,
    val contact: String,
    val userType: String,
    val avatar: String = "",
    val isVerified: Boolean = false
)

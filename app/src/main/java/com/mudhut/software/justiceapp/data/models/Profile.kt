package com.mudhut.software.justiceapp.data.models

data class Profile(
    var username: String = "",
    var uid: String = "",
    var email: String = "",
    var contact: String = "",
    var userType: String = "",
    var avatar: String = "",
    var isVerified: Boolean = false,
    var password: String = ""
)


package com.mudhut.software.justiceapp.authentication.models

data class User(
    val alias: String,
    val email: String,
    val phone: String,
    val avatar: String,
    val type: String,
)

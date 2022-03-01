package com.mudhut.software.justiceapp.data.models

data class Comment(
    val author: Author,
    val content: String,
    val timestamp: Long
)

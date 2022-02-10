package com.mudhut.software.justiceapp.data.models

import android.net.Uri

data class Post(
    var author: String = "",
    var caption: String = "",
    var created_at: String = "",
    var updated_at: String = "",
    var upvote_count: Int = 0,
    var comment_count: Int = 0,
    var media: List<Uri> = listOf()
)

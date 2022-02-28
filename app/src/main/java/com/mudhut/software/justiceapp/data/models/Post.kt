package com.mudhut.software.justiceapp.data.models

data class Post(
    var author: Author? = null,
    var caption: String = "",
    var created_at: Long = 0,
    var updated_at: Long = 0,
    var upvote_count: Int = 0,
    var comment_count: Int = 0,
    var media: List<String> = listOf(),
) {
    var key: String = ""
    var isUpvoted: Boolean = false
}

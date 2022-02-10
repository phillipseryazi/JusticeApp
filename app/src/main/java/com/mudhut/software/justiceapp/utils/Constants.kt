package com.mudhut.software.justiceapp.utils

import java.text.SimpleDateFormat
import java.util.*

const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
const val INITIAL_ELAPSED_TIME = "00:00:00"
const val NO_CAPTION_MESSAGE = "Please add a caption"
const val NO_MEDIA_MESSAGE = "Please add a video or a photo"
const val POST_UPLOADED_MESSAGE = "Post uploaded successfully"

val FILENAME = "/JusticeApp/justice-" + SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())


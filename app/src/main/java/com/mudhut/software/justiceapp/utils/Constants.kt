package com.mudhut.software.justiceapp.utils

import java.text.SimpleDateFormat
import java.util.*

const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
const val INITIAL_ELAPSED_TIME = "00:00:00"

val FILENAME = "/JusticeApp/justice-" + SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())
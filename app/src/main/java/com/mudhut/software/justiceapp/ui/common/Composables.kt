package com.mudhut.software.justiceapp.ui.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun ErrorBanner(string: String) {
    Text(string, color = MaterialTheme.colors.error, fontSize = 12.sp)
}
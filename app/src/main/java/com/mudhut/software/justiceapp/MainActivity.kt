package com.mudhut.software.justiceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mudhut.software.justiceapp.navigation.NavigationComposable
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JusticeAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavigationComposable()
                }
            }
        }
    }
}



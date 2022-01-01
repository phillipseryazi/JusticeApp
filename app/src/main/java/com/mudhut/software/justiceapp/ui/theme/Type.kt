package com.mudhut.software.justiceapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mudhut.software.justiceapp.R

val poppins = FontFamily(
    Font(R.font.poppins_regular, weight = FontWeight.Normal),
    Font(R.font.poppins_bold, weight = FontWeight.Bold),
    Font(R.font.poppins_light, weight = FontWeight.Light),
    Font(R.font.poppins_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h2 = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Thin,
        fontSize = 16.sp
    ),
    h3 = TextStyle(
        fontFamily = poppins,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)


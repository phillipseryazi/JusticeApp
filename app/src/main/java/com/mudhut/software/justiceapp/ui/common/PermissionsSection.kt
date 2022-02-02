package com.mudhut.software.justiceapp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mudhut.software.justiceapp.R

@Composable
fun PermissionsSection(label: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(
                start = 32.dp,
                end = 32.dp
            )
            .height(40.dp),
        color = MaterialTheme.colors.secondaryVariant
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(width = 8.dp))
            Image(
                modifier = Modifier
                    .size(8.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.ic_full_circle),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(width = 8.dp))
            Text(
                label,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.White
            )

        }
    }
}

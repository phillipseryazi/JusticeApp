package com.mudhut.software.justiceapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme

@Composable
fun PermissionsComposable(
    permissions: List<String>,
    grantPermissions: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "For JusticeApp to work properly please grant the following permissions.",
                modifier = Modifier.padding(
                    start = 32.dp,
                    end = 32.dp,
                    top = 32.dp
                ),
                textAlign = TextAlign.Center
            )

            permissions.forEach {
                PermissionsSection(label = it)
            }

            Button(onClick = grantPermissions) {
                Text("Grant Permissions")
            }
        }
    }
}

@Preview
@Composable
fun PermissionComposablePreview() {
    JusticeAppTheme {
        PermissionsComposable(
            permissions = listOf("Hello", "World", "There"),
            grantPermissions = {}
        )
    }
}

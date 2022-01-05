package com.mudhut.software.justiceapp.authentication.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.mudhut.software.justiceapp.R
import com.mudhut.software.justiceapp.ui.theme.JusticeAppTheme

@Composable
fun RegistrationScreen() {

    val scaffoldState = rememberScaffoldState()

    Scaffold(modifier = Modifier.fillMaxWidth(), scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            RegistrationTitleSection()
            Spacer(modifier = Modifier.height(24.dp))
            RegistrationSection()
        }
    }
}

@Composable
fun RegistrationTitleSection() {
    Text(
        stringResource(R.string.register),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.h1,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RegistrationSection() {
    Column(modifier = Modifier.width(270.dp)) {
        TextField(
            modifier = Modifier.width(270.dp),
            value = "",
            label = { Text(stringResource(R.string.username)) },
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.width(270.dp),
            value = "",
            label = { Text(stringResource(R.string.email)) },
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.width(270.dp),
            value = "",
            label = { Text(stringResource(R.string.contact)) },
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier.width(270.dp),
            value = "",
            label = { Text(stringResource(R.string.password)) },
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow {

        }
    }

}

@Composable
fun UserTypeChip(
    label: String = "",
    isSelected: Boolean = false,
    onSelectionChanged: () -> Unit = {}
) {
    Surface(
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) MaterialTheme.colors.primary else Color.LightGray
    ) {
        Row(modifier = Modifier.toggleable(
            value = false,
            onValueChange = {
                onSelectionChanged()
            }
        )) {
            Text(
                text = label,
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    end = 8.dp,
                    start = 8.dp
                ),
                style = MaterialTheme.typography.body1,
                color = if (isSelected) Color.White else Color.Black,
            )
        }
    }
}

@Composable
fun ChipGroup() {
}

@Preview
@Composable
fun UserTypeChipPreview() {
    JusticeAppTheme {
        UserTypeChip("Lorem Ipsum", false)
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    JusticeAppTheme {
        RegistrationScreen()
    }
}

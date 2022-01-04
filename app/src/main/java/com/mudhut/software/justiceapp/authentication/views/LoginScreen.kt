package com.mudhut.software.justiceapp.authentication.views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mudhut.software.justiceapp.R

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            LoginTitleSection(context = context)
            Spacer(modifier = Modifier.height(24.dp))
            LoginSection(context = context)
            Spacer(modifier = Modifier.height(24.dp))
            OrSection(context = context)
            Spacer(modifier = Modifier.height(24.dp))
            SocialLoginSection(context = context)
        }
    }
}

@Composable
fun LoginTitleSection(context: Context) {
    Text(
        context.getString(R.string.login),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.h1,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LoginSection(context: Context) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.width(270.dp),
            value = "",
            label = { Text(context.getString(R.string.username)) },
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.width(270.dp),
            value = "",
            label = { Text(context.getString(R.string.password)) },
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(
                context.getString(R.string.login),
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            context.getString(R.string.go_to_registration),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { },
            style = MaterialTheme.typography.body1
        )
    }

}

@Composable
fun OrSection(context: Context) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_horizontal_rule
            ),
            contentDescription = null,
            modifier = Modifier.size(
                width = 40.dp,
                height = 20.dp
            )
        )
        Text(
            context.getString(R.string.text_or),
            style = MaterialTheme.typography.body1
        )
        Image(
            painter = painterResource(
                id = R.drawable.ic_horizontal_rule
            ),
            contentDescription = null,
            modifier = Modifier.size(width = 40.dp, height = 20.dp)
        )
    }
}

@Composable
fun SocialLoginSection(context: Context) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            ),
            modifier = Modifier.width(270.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_icon_google),
                contentDescription = context.getString(R.string.google),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                context.getString(R.string.continue_with_google),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp
                ),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                backgroundColor = Color.White
            ),
            modifier = Modifier.width(270.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_icon_anonymous
                ),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                context.getString(R.string.continue_anonymously),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 4.dp
                ),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
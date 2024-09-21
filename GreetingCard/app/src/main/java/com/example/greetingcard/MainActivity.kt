package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greetingcard.ui.theme.GreetingCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    BusinessCardApp()
                }
            }
        }
    }
}

@Composable
fun BusinessCardApp(){
    Box(
        modifier = Modifier
            .background(Color(83, 211, 153, 70))
    )
    PrimaryInfo()
    UserContactInfo()
}

@Composable
fun PrimaryInfo(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = painterResource(R.drawable.android_logo)
        Box(
            modifier = Modifier
                .size(120.dp, 120.dp)
                .background(color = Color(0xFF0d2b4b))
                .clip(CircleShape)
        ) {
            Image(
                painter = image,
                contentDescription = stringResource(R.string.bc_profile_txt),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
        Text(
            text = stringResource(R.string.bc_username_txt),
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            fontFamily = FontFamily.Serif,
            fontSize = 32.sp
        )
        Text(
            text = stringResource(R.string.bc_position_txt), color = Color(0xFF1c8624),
            fontWeight = FontWeight.Bold
        )
    }
    
}

@Composable
fun UserContactInfo(modifier: Modifier = Modifier){
    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 60.dp, end = 50.dp)
        ,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier.padding(bottom = 16.dp)
        ) {
            Icon (
                Icons.Rounded.Phone,
                "Phone",
                tint = Color(0xFF1c8624)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(R.string.bc_phone_txt),

            )
            Spacer(modifier = Modifier.width(20.dp))
        }
        Row(
            modifier.padding(bottom = 16.dp)
        ) {
            Icon (
                Icons.Rounded.Share,
                "Share",
                tint = Color(0xFF1c8624)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(R.string.bc_share_txt)

            )
        }
        Row(
            modifier.padding(bottom = 50.dp)
        ) {
            Icon (
                Icons.Rounded.Email,
                "Email",
                tint = Color(0xFF1c8624)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(R.string.bc_email_txt)

            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessCardPreview() {
    GreetingCardTheme {
        BusinessCardApp()
    }
}
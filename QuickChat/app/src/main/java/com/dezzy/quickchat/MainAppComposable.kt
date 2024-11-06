package com.dezzy.quickchat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dezzy.quickchat.ui.screens.auth.signin.SignInScreen
import com.dezzy.quickchat.ui.screens.auth.signup.SignUpScreen
import com.dezzy.quickchat.ui.screens.chat.ChatScreen
import com.dezzy.quickchat.ui.screens.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        val user = FirebaseAuth.getInstance().currentUser
        val start = if (user != null) "home" else "login"

        NavHost(navController = navController, startDestination = start) {
            composable(route = "login") {
                SignInScreen(navController)
            }
            composable(route = "signup") {
                SignUpScreen(navController)
            }
            composable(route = "home") {
                HomeScreen(navController)
            }
            composable(route = "chat/{channelId}", arguments = listOf(
                navArgument("channelId") {
                    type = NavType.StringType
                }
            )) {
                val channelId = it.arguments?.getString("channelId") ?: ""
                ChatScreen(navController, channelId)
            }
        }

        Column(modifier = Modifier.padding(it)) {

        }
    }
}
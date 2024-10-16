package com.dezzy.quickchat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dezzy.quickchat.ui.screens.auth.signin.SignInScreen
import com.dezzy.quickchat.ui.screens.auth.signup.SignUpScreen

@Composable
fun MainApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "login") {
            composable(route = "login") {
                SignInScreen(navController)
            }
            composable(route = "signup") {
                SignUpScreen(navController)
            }
        }

        Column(modifier = Modifier.padding(it)) {

        }
    }
}
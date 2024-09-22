package com.dezzy.chatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.dezzy.chatbot.ui.ChatScreen
import com.dezzy.chatbot.ui.ChatViewModel
import com.dezzy.chatbot.ui.theme.ChatBotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        setContent {
            ChatBotTheme {
                Scaffold(modifier = Modifier.fillMaxSize().imePadding()) { innerPadding ->
                    ChatScreen(modifier = Modifier.padding(innerPadding), chatViewModel)
                }
            }
        }
    }
}

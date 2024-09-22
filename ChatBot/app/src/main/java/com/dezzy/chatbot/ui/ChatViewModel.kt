package com.dezzy.chatbot.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel:ViewModel() {
    private val generativeModel : GenerativeModel = GenerativeModel(
        modelName = Constants.MODEL_NAME,
        apiKey = Constants.API_KEY,
    )

    val messageList by lazy {
        mutableStateListOf<Message>()
    }

    fun sendMessage(message:String){
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(if (it.isUser) "user" else "model"){text(it.message)}
                    }
                )

                messageList.add(Message(message,true))
                messageList.add(Message("Typing...",false))
                val response = chat.sendMessage(message)
                messageList.removeLast()
                messageList.add(Message(response.text.toString(),false))
            } catch (e: Exception) {
                messageList.removeLast()
                messageList.add(Message(
                    "Sorry, I couldn't process your request.",
                    false,
                ))
                messageList.add(Message(
                    "Error:" + e.message.toString(),
                    false,
                ))
            }
        }

    }
}
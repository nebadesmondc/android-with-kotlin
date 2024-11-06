package com.dezzy.quickchat.model

data class Message (
    val id: String = "",
    val message: String = "",
    val senderId: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val senderName: String = "",
    val senderImage: String = "",
    val imageUrl: String = ""
)
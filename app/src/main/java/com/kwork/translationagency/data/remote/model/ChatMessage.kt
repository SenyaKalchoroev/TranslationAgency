package com.kwork.translationagency.data.remote.model

data class ChatMessage(
    val messageId: String = "",
    val senderUid:  String = "",
    val text:       String = "",
    val type:       String = "text",
    val timestamp:  Long   = System.currentTimeMillis()
)


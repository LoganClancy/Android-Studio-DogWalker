package com.example.cosc341_step4.chat

data class Message(
    val senderId: String = "",
    val senderName: String = "",
    val content: String = "",
    val type: MessageType = MessageType.TEXT,
    val timestamp: Long = System.currentTimeMillis(),
    val mediaUri: String? = null
)

enum class MessageType {
    TEXT,
    IMAGE,
    VOICE,
    VIDEO
}
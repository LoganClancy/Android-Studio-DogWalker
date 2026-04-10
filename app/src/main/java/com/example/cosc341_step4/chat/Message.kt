package com.group18.petapp.chat

data class Message(
    val id: String = java.util.UUID.randomUUID().toString(),
    val senderId: String,
    val senderName: String,
    val content: String = "",
    val type: MessageType = MessageType.TEXT,
    val mediaUri: String? = null,       // for image/video/voice
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

enum class MessageType {
    TEXT,
    IMAGE,
    VIDEO,
    VOICE
}

package com.example.cosc341_step4.chat

data class Conversation(
    val id: String = "",
    val name: String = "",
    val lastMessage: String = "",
    val lastMessageTime: String = "",
    val unreadCount: Int = 0,
    val isGroup: Boolean = false,
    val members: List<String> = emptyList()
)
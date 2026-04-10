package com.example.cosc341_step4.chat

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group18.petapp.R

class ChatListActivity : AppCompatActivity() {

    private lateinit var rvConversations: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: ConversationAdapter

    // Sample data — replace with Firebase/backend data
    private val allConversations = mutableListOf(
        Conversation(
            id = "conv_harrison",
            name = "Harrison Kayihura",
            lastMessage = "Hey do you remember last time...",
            lastMessageTime = "5:00 PM",
            unreadCount = 0
        ),
        Conversation(
            id = "conv_logan",
            name = "Logan Clancy",
            lastMessage = "I love my dog",
            lastMessageTime = "3:59 PM",
            unreadCount = 0
        ),
        Conversation(
            id = "group_18",
            name = "Group 18",
            lastMessage = "think we have to done all the jo...",
            lastMessageTime = "3:00",
            unreadCount = 8,
            isGroup = true,
            members = listOf("Harrison Kayihura", "Logan Clancy", "Tanay Desai", "Tom Huang", "Dominic J")
        ),
        Conversation(
            id = "conv_tanay",
            name = "Tanay Desai",
            lastMessage = "",
            lastMessageTime = "",
            unreadCount = 0
        ),
        Conversation(
            id = "conv_tom",
            name = "Tom Huang",
            lastMessage = "",
            lastMessageTime = "",
            unreadCount = 0
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        rvConversations = findViewById(R.id.rvConversations)
        searchView = findViewById(R.id.searchView)

        setupRecyclerView()
        setupSearch()
    }

    private fun setupRecyclerView() {
        adapter = ConversationAdapter(allConversations.toMutableList()) { conversation ->
            // Open chat room
            val intent = Intent(this, ChatRoomActivity::class.java).apply {
                putExtra(ChatRoomActivity.EXTRA_CONVERSATION_ID, conversation.id)
                putExtra(ChatRoomActivity.EXTRA_CONVERSATION_NAME, conversation.name)
                putExtra(ChatRoomActivity.EXTRA_IS_GROUP, conversation.isGroup)
            }
            startActivity(intent)
        }

        rvConversations.layoutManager = LinearLayoutManager(this)
        rvConversations.adapter = adapter
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filtered = if (newText.isNullOrBlank()) {
                    allConversations.toMutableList()
                } else {
                    allConversations.filter {
                        it.name.contains(newText, ignoreCase = true) ||
                        it.lastMessage.contains(newText, ignoreCase = true)
                    }.toMutableList()
                }
                adapter.updateConversations(filtered)
                return true
            }
        })
    }
}

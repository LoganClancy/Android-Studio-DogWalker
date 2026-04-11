package com.example.cosc341_step4.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cosc341_step4.R

class ConversationAdapter(
    private var conversations: MutableList<Conversation>,
    private val onClick: (Conversation) -> Unit
) : RecyclerView.Adapter<ConversationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val lastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)
        val time: TextView = itemView.findViewById(R.id.tvTime)
        val unread: TextView = itemView.findViewById(R.id.tvUnreadBadge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conversation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conversation = conversations[position]

        holder.name.text = conversation.name
        holder.lastMessage.text = conversation.lastMessage
        holder.time.text = conversation.lastMessageTime

        if (conversation.unreadCount > 0) {
            holder.unread.visibility = View.VISIBLE
            holder.unread.text = conversation.unreadCount.toString()
        } else {
            holder.unread.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onClick(conversation)
        }
    }

    override fun getItemCount(): Int = conversations.size

    fun updateConversations(newList: MutableList<Conversation>) {
        conversations = newList
        notifyDataSetChanged()
    }
}
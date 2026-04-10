package com.example.cosc341_step4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group18.petapp.R

class ConversationAdapter(
    private val conversations: MutableList<Conversation>,
    private val onItemClick: (Conversation) -> Unit
) : RecyclerView.Adapter<ConversationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val ivGroupIcon: ImageView = itemView.findViewById(R.id.ivGroupIcon)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvLastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvUnreadBadge: TextView = itemView.findViewById(R.id.tvUnreadBadge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_conversation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conversation = conversations[position]

        holder.tvName.text = conversation.name
        holder.tvLastMessage.text = conversation.lastMessage
        holder.tvTime.text = conversation.lastMessageTime

        // Show group icon badge for group chats
        holder.ivGroupIcon.visibility = if (conversation.isGroup) View.VISIBLE else View.GONE

        // Show/hide unread badge
        if (conversation.unreadCount > 0) {
            holder.tvUnreadBadge.visibility = View.VISIBLE
            holder.tvUnreadBadge.text = if (conversation.unreadCount > 99) "99+"
            else conversation.unreadCount.toString()
        } else {
            holder.tvUnreadBadge.visibility = View.GONE
        }

        // Highlight currently-logged-in user's row
        if (conversation.id == "self") {
            holder.itemView.setBackgroundResource(R.drawable.bg_self_highlight)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.selector_conversation_item)
        }

        holder.itemView.setOnClickListener { onItemClick(conversation) }
    }

    override fun getItemCount() = conversations.size

    fun updateConversations(newList: List<Conversation>) {
        conversations.clear()
        conversations.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateUnreadCount(conversationId: String, count: Int) {
        val index = conversations.indexOfFirst { it.id == conversationId }
        if (index >= 0) {
            conversations[index] = conversations[index].copy(unreadCount = count)
            notifyItemChanged(index)
        }
    }
}

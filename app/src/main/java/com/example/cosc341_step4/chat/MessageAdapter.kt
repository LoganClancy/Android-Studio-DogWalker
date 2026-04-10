package com.example.cosc341_step4.chat

import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cosc341_step4.R

class MessageAdapter(
    private val messages: MutableList<Message>,
    private val currentUserId: String,
    private val isGroupChat: Boolean = false
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bubbleContainer: LinearLayout = itemView.findViewById(R.id.bubbleContainer)
        val tvSenderName: TextView = itemView.findViewById(R.id.tvSenderName)
        val tvMessageText: TextView = itemView.findViewById(R.id.tvMessageText)
        val ivMessageImage: ImageView = itemView.findViewById(R.id.ivMessageImage)
        val voiceNoteContainer: LinearLayout = itemView.findViewById(R.id.voiceNoteContainer)
        val btnPlayVoice: ImageButton = itemView.findViewById(R.id.btnPlayVoice)
        val seekBarVoice: SeekBar = itemView.findViewById(R.id.seekBarVoice)
        val tvVoiceDuration: TextView = itemView.findViewById(R.id.tvVoiceDuration)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
    }

    override fun getItemViewType(position: Int): Int =
        if (messages[position].senderId == currentUserId) VIEW_TYPE_SENT else VIEW_TYPE_RECEIVED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        val isSent = message.senderId == currentUserId

        val params = holder.bubbleContainer.layoutParams as LinearLayout.LayoutParams
        if (isSent) {
            params.marginStart = 60
            params.marginEnd = 0
            holder.bubbleContainer.setBackgroundResource(R.drawable.bg_bubble_sent)
        } else {
            params.marginStart = 0
            params.marginEnd = 60
            holder.bubbleContainer.setBackgroundResource(R.drawable.bg_bubble_received)
        }
        holder.bubbleContainer.layoutParams = params

        if (isGroupChat && !isSent) {
            holder.tvSenderName.visibility = View.VISIBLE
            holder.tvSenderName.text = message.senderName
        } else {
            holder.tvSenderName.visibility = View.GONE
        }

        holder.tvMessageText.visibility = View.GONE
        holder.ivMessageImage.visibility = View.GONE
        holder.voiceNoteContainer.visibility = View.GONE

        when (message.type) {
            MessageType.TEXT -> {
                holder.tvMessageText.visibility = View.VISIBLE
                holder.tvMessageText.text = message.content
            }
            MessageType.IMAGE -> {
                holder.ivMessageImage.visibility = View.VISIBLE
                message.mediaUri?.let { holder.ivMessageImage.setImageURI(Uri.parse(it)) }
            }
            MessageType.VOICE -> {
                holder.voiceNoteContainer.visibility = View.VISIBLE
                setupVoicePlayer(holder, message)
            }

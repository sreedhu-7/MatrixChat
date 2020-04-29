package com.matrix.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matrix.chat.model.Message

class ChatAdapter(
    private val context: Context,
    var messageList: List<Message>,
    var userKey: String
) :
    RecyclerView.Adapter<ChatViewHolder>() {
    lateinit var onItemClickListener: (Int) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val item =
            if (viewType == ItemType.LEFT) R.layout.item_message_left else R.layout.item_message_right
        return ChatViewHolder(
            LayoutInflater.from(context).inflate(item, parent, false),
            onItemClickListener
        )
    }


    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].senderId == userKey) ItemType.RIGHT else ItemType.LEFT
    }

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.setText(messageList[position].message, messageList[position].timeStamp)
    }

    fun addData(messageList: List<Message>) {
        this.messageList = messageList
        notifyDataSetChanged()
    }

    object ItemType {
        const val LEFT = 1
        const val RIGHT = 2
    }
}

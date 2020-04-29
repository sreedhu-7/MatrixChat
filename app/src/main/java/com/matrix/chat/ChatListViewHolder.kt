package com.matrix.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_chat_list.view.*

class ChatListViewHolder(view: View, onItemClickListener: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            onItemClickListener(layoutPosition)
        }
    }

    fun setText(name: String = "", lastMessage: String = "") {
        itemView.name.text = name
        itemView.lastMessage.text = lastMessage
    }
}

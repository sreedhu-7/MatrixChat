package com.matrix.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_message_left.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatViewHolder(view: View, onItemClickListener: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            onItemClickListener(layoutPosition)
        }
    }

    fun setText(message: String = "", timeStamp: Long) {
        itemView.message.text = message
        val format = SimpleDateFormat("MM/dd/YY hh:mm", Locale.getDefault())
        itemView.time.text = format.format(timeStamp)
    }
}

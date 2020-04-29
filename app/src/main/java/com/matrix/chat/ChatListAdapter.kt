package com.matrix.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matrix.chat.model.User

class ChatListAdapter(private val context: Context, var userList: List<User>, var userKey: String) :
    RecyclerView.Adapter<ChatListViewHolder>() {
    lateinit var onItemClickListener: (Int) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder =
        ChatListViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_chat_list, parent, false),
            onItemClickListener
        )


    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val nickName =
            if (userKey == userList[position].key) holder.itemView.context.getString(R.string.label_you)
            else userList[position].nickName
        holder.setText(nickName, "")
    }

    fun addData(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}

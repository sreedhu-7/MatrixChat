package com.matrix.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.matrix.chat.databinding.ActivityChatBinding
import com.matrix.chat.model.Message
import com.matrix.chat.model.User
import com.matrix.chat.viewmodel.ChatViewModel
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat_list.toolbar

class ChatActivity : AppCompatActivity() {

    private lateinit var adapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding =
            DataBindingUtil.setContentView<ActivityChatBinding>(this, R.layout.activity_chat)
        val model = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        dataBinding.chatModel = model
        model.user = intent.extras?.getSerializable("user") as User
        model.getChatMessages()
        toolbar.title = model.user.nickName
        setSupportActionBar(toolbar)

        adapter = ChatAdapter(this, model.messageListLiveData.value!!, model.getUserKey()).apply {
            onItemClickListener = {}
        }
        chatRecyclerView.adapter = adapter
        model.messageListLiveData.observe(this, Observer {
            handleResult(it)
        })
    }

    private fun handleResult(messageList: List<Message>) {
        adapter.addData(messageList)
        if (messageList.isNotEmpty()) {
            chatRecyclerView.smoothScrollToPosition(messageList.size - 1)
        }

    }
}

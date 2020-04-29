package com.matrix.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.matrix.chat.model.User
import com.matrix.chat.viewmodel.ChatListViewModel
import kotlinx.android.synthetic.main.activity_chat_list.*

class ChatListActivity : AppCompatActivity() {

    private lateinit var adapter: ChatListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)
        setSupportActionBar(toolbar)

        val userKey = getSharedPreferences(
            Constants.PREF_NAME,
            Context.MODE_PRIVATE
        ).getString(Constants.USER_ID, "")
        if (userKey.isNullOrBlank()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val model = ViewModelProviders.of(this).get(ChatListViewModel::class.java)

        adapter = ChatListAdapter(this, model.userListLiveData.value!!, userKey!!).apply {
            onItemClickListener = {
                if (this.userList[it].key != userKey) {
                    val intent = Intent(this@ChatListActivity, ChatActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("user", this.userList[it])
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
        }
        recyclerView.adapter = adapter
        model.userListLiveData.observe(this, Observer {
            handleResult(it)
        })
    }

    private fun handleResult(userList: List<User>) {
        adapter.addData(userList)
    }
}

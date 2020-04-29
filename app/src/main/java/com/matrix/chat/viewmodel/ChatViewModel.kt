package com.matrix.chat.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.matrix.chat.Constants
import com.matrix.chat.model.Message
import com.matrix.chat.model.User
import java.util.*
import kotlin.collections.ArrayList


class ChatViewModel(application: Application) : AndroidViewModel(application),
    ChildEventListener {
    private var databaseRef: DatabaseReference =
        FirebaseDatabase.getInstance().reference
    var messageList = ArrayList<Message>()
    var messageListLiveData = MutableLiveData<List<Message>>()
    lateinit var user: User
    var messageText: ObservableField<String>

    init {
        messageListLiveData.value = ArrayList()
        messageText = ObservableField()
    }

    fun getChatMessages() {
        databaseRef.child("chats").child(createChatKey(getUserKey(), user.key))
            .addChildEventListener(this)
    }

    fun onSendButtonClick(view: View) {
        messageText.get()?.let {
            if (messageText.get()!!.isNotBlank()) {
                val key = databaseRef.key ?: Calendar.getInstance().timeInMillis.toString()
                val messageObject = Message(key, messageText.get()!!.trim(), getUserKey())
                databaseRef.child("chats").child(createChatKey(getUserKey(), user.key)).child(key)
                    .setValue(messageObject)
                messageText.set("")
            }
        }

    }

    override fun onCancelled(p0: DatabaseError) {
    }

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
        if (dataSnapshot.value.toString().isNotBlank()) {
            val user = dataSnapshot.getValue(Message::class.java)
            user?.let {
                messageList.add(user)
                messageListLiveData.value = messageList
            }
        }

    }

    override fun onChildRemoved(p0: DataSnapshot) {
    }

    private fun createChatKey(user1: String, user2: String): String {
        return if (user1.length > user2.length) {
            "$user1-$user2"
        } else {
            "$user2-$user1"
        }
    }

    fun getUserKey(): String {
        return getApplication<Application>().getSharedPreferences(
            Constants.PREF_NAME,
            Context.MODE_PRIVATE
        ).getString(Constants.USER_ID, "")!!
    }

}

package com.matrix.chat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.matrix.chat.model.User


class ChatListViewModel(application: Application) : AndroidViewModel(application),
    ChildEventListener {
    private var databaseRef: DatabaseReference =
        FirebaseDatabase.getInstance().reference
    var userList = ArrayList<User>()
    var userListLiveData = MutableLiveData<List<User>>()

    init {
        userListLiveData.value = ArrayList();
        getUsers()
    }

    private fun getUsers() {
        databaseRef.child("users").addChildEventListener(this)
    }

    override fun onCancelled(p0: DatabaseError) {
    }

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
        val user = dataSnapshot.getValue(User::class.java)
        user?.let {
            userList.add(user)
            userListLiveData.value = userList
        }
    }

    override fun onChildRemoved(p0: DataSnapshot) {
    }

    fun createChatKey(user1: String, user2: String): String {
        return if (user1.length > user2.length) {
            "$user1-$user2"
        } else {
            "$user2-$user1"
        }
    }

}

package com.matrix.chat.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.matrix.chat.ConnectivityUtils
import com.matrix.chat.Constants
import com.matrix.chat.model.User
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var databaseRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("users")
    val createUserResult = MutableLiveData<String>()

    fun createUser(nickname: String) {
        if (!ConnectivityUtils.isConnected(getApplication())) {
            createUserResult.value = Constants.NETWORK_ERROR
        }
        val key = nickname.plus(Calendar.getInstance().timeInMillis)
        val user = User(key, nickname)
        databaseRef.child(key).setValue(user) { databaseError, _ ->
            if (databaseError != null) {
                createUserResult.value = Constants.FAILURE
            } else {
                saveNickName(key, nickname)
                createUserResult.value = Constants.SUCCESS
            }
        }
    }

    private fun saveNickName(key: String, nickName: String) {
        val sharedPref: SharedPreferences =
            getApplication<Application>().getSharedPreferences(
                Constants.PREF_NAME,
                Context.MODE_PRIVATE
            )
        val editor = sharedPref.edit()
        editor.putString(Constants.USER_NICK_NAME, nickName)
        editor.putString(Constants.USER_ID, key)
        editor.apply()
    }

}

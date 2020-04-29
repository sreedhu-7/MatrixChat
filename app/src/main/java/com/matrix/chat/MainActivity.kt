package com.matrix.chat

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.matrix.chat.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val model = ViewModelProviders.of(this).get(MainViewModel::class.java)

        startChatButton.setOnClickListener {
            model.createUser(nickName.text.toString())
        }

        nickName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val content = s.toString()
                startChatButton?.visibility = if (content.length >= 3) View.VISIBLE else View.GONE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        model.createUserResult.observe(this, Observer {
            when (it) {
                Constants.SUCCESS -> {
                    startActivity(Intent(this, ChatListActivity::class.java))
                    finish()
                }
                Constants.FAILURE -> {
                    Toast.makeText(
                        this,
                        getString(R.string.error_message_something),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }


}

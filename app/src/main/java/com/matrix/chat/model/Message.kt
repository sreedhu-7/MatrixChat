package com.matrix.chat.model

import java.io.Serializable
import java.util.*

data class Message(
    var key: String = "",
    var message: String = "",
    var senderId: String = "",
    var timeStamp: Long = Calendar.getInstance().timeInMillis
) :
    Serializable

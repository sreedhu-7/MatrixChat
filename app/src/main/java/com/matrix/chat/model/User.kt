package com.matrix.chat.model

import java.io.Serializable

data class User(
    var key: String = "",
    var nickName: String = "",
    var original: String = "",
    var thumbnail: String = ""
) : Serializable

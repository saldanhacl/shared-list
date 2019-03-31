package br.com.go5.sharedlist.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Long,
    @SerializedName("nome")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("senha")
    var password: String
)
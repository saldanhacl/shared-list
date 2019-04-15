package br.com.go5.sharedlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("admin") val isAdmin: Boolean,
    @SerializedName("usuario") val user: User
)
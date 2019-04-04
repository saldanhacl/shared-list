package br.com.go5.sharedlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @SerializedName("id") @PrimaryKey val id: Long,
    @SerializedName("nome") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("senha") var password: String
)
package br.com.go5.sharedlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CommentResponse (
    val comment: Comment,
    val user: User
)
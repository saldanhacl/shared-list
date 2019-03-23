package br.com.go5.sharedlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val price: Double
)
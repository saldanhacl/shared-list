package br.com.go5.sharedlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (
    @PrimaryKey
    val id: Long,
    val name: String,
    val price: Double
) {
    fun getPriceString(): String {
        return price.toString()
    }
}
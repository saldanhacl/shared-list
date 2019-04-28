package br.com.go5.sharedlist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product (
    @PrimaryKey
    val id: Long,
    @SerializedName("nome")
    val name: String,
    @SerializedName("preco")
    val price: Double
) {
    fun getPriceString(): String {
        return price.toString()
    }
}
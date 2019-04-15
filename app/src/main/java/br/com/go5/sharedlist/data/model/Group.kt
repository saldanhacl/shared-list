package br.com.go5.sharedlist.data.model

import androidx.room.*
import br.com.go5.sharedlist.utils.ListConverter
import com.beust.klaxon.Json
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("id")
    val id: Long,

    @SerializedName("nome")
    var name: String,

    @SerializedName("listasDeCompras")
    @Expose
    var listasDeCompras: List<ShoppingList>,

    @SerializedName("usuarios")
    @Expose
    var usuarios: List<UserResponse>

//    @SerializedName("usuarios")
//    @Ignore
//    @TypeConverters(ListConverter::class)
//    var users: List<User>
)
package br.com.go5.sharedlist.data.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

class MultipleItem : MultiItemEntity {

    private var itemType: Int = 0

    @SerializedName("nome")
    var name: String? = null
    @SerializedName("preco")
    var price: Double? = null

//    @SerializedName("user")
    var username: String? = null
//    @SerializedName("comentario")
    var comment: String? = null

    constructor(itemType: Int, name: String, price: Double) {
        this.itemType = itemType
        this.name = name
        this.price = price
    }

    constructor(itemType: Int, username: String, comment: String) {
        this.itemType = itemType
        this.username = username
        this.comment = comment
    }

    constructor(itemType: Int) {
        this.itemType = itemType
    }

    override fun getItemType(): Int {
        return itemType
    }

    companion object {
        val PRODUCT = 1
        val COMMENT = 2
    }
}
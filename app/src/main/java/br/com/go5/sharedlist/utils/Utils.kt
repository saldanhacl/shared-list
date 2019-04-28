package br.com.go5.sharedlist.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import br.com.go5.sharedlist.data.model.MultipleItem
import br.com.go5.sharedlist.data.model.Product

class Utils(private val context: Context) {

    fun isConnectedToInternet(): Boolean {
        val connectivity = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.activeNetworkInfo
        return info!=null && info.isConnected
    }

    fun productsToListOfMultipleItems(products: List<Product>): MutableList<MultipleItem> {
        val list: MutableList<MultipleItem> = mutableListOf()
        products.forEach {
            list.add(MultipleItem(MultipleItem.PRODUCT, it.name, it.price))
        }
        return list
    }
}
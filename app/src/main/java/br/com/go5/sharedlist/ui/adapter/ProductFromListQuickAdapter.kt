package br.com.go5.sharedlist.ui.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.data.model.ShoppingList
import br.com.go5.sharedlist.data.viewmodel.ShoppingListViewModel
import br.com.go5.sharedlist.di.viewModelModule
import br.com.go5.sharedlist.ui.activity.AddProductActivity
import br.com.go5.sharedlist.ui.activity.ShoppingListDetailActivity
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


class ProductFromListQuickAdapter(val list: MutableList<Product>, private val shoppingListDetailActivity: ShoppingListDetailActivity) : BaseQuickAdapter<Product, BaseViewHolder>(R.layout.products_list, list) {

    override fun convert(helper: BaseViewHolder, item: Product) {
        helper.setText(R.id.txtProductName, item.name)
        helper.setText(R.id.txtPrice, item.price.toString())
        val checkBox = helper.getView<CheckBox>(R.id.checkProduct)
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                shoppingListDetailActivity.deleteProductFromList(item)
                list.remove(item)
                notifyDataSetChanged()
            }
        }
    }

}
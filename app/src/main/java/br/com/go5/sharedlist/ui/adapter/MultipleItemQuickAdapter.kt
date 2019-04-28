package br.com.go5.sharedlist.ui.adapter

import android.content.Context
import android.view.View
import android.widget.CheckBox
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.MultipleItem
import br.com.go5.sharedlist.ui.activity.AddProductActivity
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class MultipleItemQuickAdapter(data: List<MultipleItem>) : BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder>(data) {

    init {
        addItemType(MultipleItem.PRODUCT, R.layout.products_list)
        addItemType(MultipleItem.COMMENT, R.layout.comment)
    }

    override fun convert(helper: BaseViewHolder, item: MultipleItem) {
        when (helper.itemViewType) {
            MultipleItem.PRODUCT -> {
                setupProduct(helper, item)
            }
            MultipleItem.COMMENT ->  {
                setupComment(helper, item)
            }
        }
    }

    private fun setupProduct(helper: BaseViewHolder, item: MultipleItem) {
        helper.setText(R.id.txtProductName, item.name)
        helper.setText(R.id.txtPrice, item.price.toString())
        val checkBox = helper.getView<CheckBox>(R.id.checkProduct)
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

                notifyDataSetChanged()
            }
        }
    }

    private fun setupComment(helper: BaseViewHolder, item: MultipleItem) {
        helper.setText(R.id.txtUserName, item.username)
        helper.setText(R.id.txtComment, item.comment)
    }

}
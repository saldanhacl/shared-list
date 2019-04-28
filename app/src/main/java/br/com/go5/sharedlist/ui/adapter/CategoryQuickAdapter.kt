package br.com.go5.sharedlist.ui.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Category
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


class CategoryQuickAdapter(list: List<Category>) : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_category, list) {

    override fun convert(helper: BaseViewHolder, item: Category) {
        helper.setText(R.id.txtCategoryName, item.name)
        val recyclerView = helper.getView<RecyclerView>(R.id.rvProducts)
        recyclerView.layoutManager = LinearLayoutManager(helper.itemView.context)
        recyclerView.setHasFixedSize(true)
        val productAdapter = ProductQuickAdapter(item.products, true)
        recyclerView.adapter = productAdapter
    }

}
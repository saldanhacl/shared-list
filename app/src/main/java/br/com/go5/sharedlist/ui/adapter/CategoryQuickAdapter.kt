package br.com.go5.sharedlist.ui.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Category
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


class CategoryQuickAdapter(list: List<Category>) : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.item_category, list) {

    override fun convert(helper: BaseViewHolder, item: Category) {
        helper.setText(R.id.txtCategoryName, item.name)
    }

}
package br.com.go5.sharedlist.ui.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Group
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


class GroupQuickAdapter(list: List<Group>) : BaseItemDraggableAdapter<Group, BaseViewHolder>(R.layout.groups_list, list) {

    override fun convert(helper: BaseViewHolder, item: Group) {
        helper.setText(R.id.txtGroupName, item.name)
    }

    override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder?) {
        if (mOnItemSwipeListener != null && itemSwipeEnabled) {
            mOnItemSwipeListener.onItemSwiped(viewHolder, getViewHolderPosition(viewHolder))
        }
        val pos = getViewHolderPosition(viewHolder)

        // Method - private boolean inRange(int position)
        if (pos >= 0 && pos < mData.size) {
            mData.removeAt(pos)
            notifyItemRemoved(viewHolder!!.adapterPosition)
        }
    }

}
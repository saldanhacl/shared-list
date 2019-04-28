package br.com.go5.sharedlist.ui.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Comment
import br.com.go5.sharedlist.data.model.CommentResponse
import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


class CommentQuickAdapter(list: List<CommentResponse>) : BaseQuickAdapter<CommentResponse, BaseViewHolder>(R.layout.comment, list) {

    override fun convert(helper: BaseViewHolder, item: CommentResponse) {
        helper.setText(R.id.txtUserName, item.user.name)
        helper.setText(R.id.txtComment, item.comment.comment)
    }

}
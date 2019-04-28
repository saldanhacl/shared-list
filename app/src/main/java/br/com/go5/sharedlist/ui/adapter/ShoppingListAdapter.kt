package br.com.go5.sharedlist.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.BR
import br.com.go5.sharedlist.data.model.ShoppingList
import br.com.go5.sharedlist.data.model.User
import com.github.nitrico.lastadapter.LastAdapter


class ShoppingListAdapter(private var shoppingLists: List<ShoppingList>,
                          private val listener: OnItemSelected) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>()  {

    var selectedIndex: Int = -1

    interface OnItemSelected {
        fun onSelect(position: Int, showMenu: Boolean)
    }

    fun loadItems(newItems: List<ShoppingList>) {
        shoppingLists += newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = ShoppingListViewHolder(inflater, parent)
        viewHolder.setIsRecyclable(false)
        return viewHolder

    }

    override fun getItemCount(): Int {
        return shoppingLists.size
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val shoppingList: ShoppingList = shoppingLists[position]
        holder.bind(shoppingList, position, listener)
    }

    inner class ShoppingListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.shopping_list, parent, false)) {

        private var txtShoppingListName: TextView = itemView.findViewById(R.id.txtProductName)
        private var rvProducts: RecyclerView = itemView.findViewById(R.id.rvProducts)
        private var constLayout: ConstraintLayout = itemView.findViewById(R.id.constLayout)

        fun bind(shoppingList: ShoppingList, position: Int, listener: OnItemSelected) {
            txtShoppingListName.text = shoppingList.name
            LastAdapter(emptyList(), BR.product)
                .map<User>(R.layout.shopping_list_products_list)
                .into(rvProducts)
            setListeners(listener, position)
            if (selectedIndex == position) changeColorToSelected()
        }

        private fun setListeners(listener: OnItemSelected, position: Int) {
            constLayout.setOnClickListener {
//                if (selectedIndex == position) {
//                    listener.onSelect(position, false)
//                    selectedIndex = -1
//                    notifyDataSetChanged()
//                } else {
//                    selectedIndex = position
//                    listener.onSelect(position, true)
//                    notifyDataSetChanged()
//                }
            }
        }

        private fun changeColorToSelected() {
            txtShoppingListName.setTextColor(Color.WHITE)
            constLayout.setBackgroundColor(Color.rgb(255, 87, 34))
        }

        private fun changeColorToDefault() {
            txtShoppingListName.setTextColor(Color.BLACK)
            constLayout.setBackgroundColor(Color.WHITE)
        }
    }


}
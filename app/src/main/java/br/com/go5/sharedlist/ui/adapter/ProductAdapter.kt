package br.com.go5.sharedlist.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Product


class ProductAdapter(private var products: List<Product>,
                     private val listener: OnItemSelected) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()  {

    var selectedIndex: Int = -1

    interface OnItemSelected {
        fun onSelect(position: Int, showMenu: Boolean)
    }

    fun loadItems(newItems: List<Product>) {
        products += newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = ProductViewHolder(inflater, parent)
        viewHolder.setIsRecyclable(false)
        return viewHolder

    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product: Product = products[position]
        holder.bind(product, position, listener)
    }

    inner class ProductViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.products_list, parent, false)) {

        private var isSelected: Boolean
        private var txtName: TextView? = null
        private var txtPrice: TextView? = null
        private var constLayout: ConstraintLayout? = null

        init {
            txtName = itemView.findViewById(R.id.txtShoppingListName)
            txtPrice = itemView.findViewById(R.id.txtPrice)
            constLayout = itemView.findViewById(R.id.constLayout)
            isSelected = false
        }

        fun bind(product: Product, position: Int, listener: OnItemSelected) {
            txtName?.text = product.name
            txtPrice?.text = "R$" + product.price.toString()
            setListeners(listener, position)
            if (selectedIndex == position) changeColorToSelected()
        }

        private fun setListeners(listener: OnItemSelected, position: Int) {
//            constLayout?.setOnLongClickListener {
//                isSelected = true
//                changeColorToSelected()
//                listener.onSelect(position, true)
//                true
//            }true

            constLayout?.setOnClickListener {
                if (selectedIndex == position) {
                    listener.onSelect(position, false)
                    selectedIndex = -1
                    notifyDataSetChanged()
                } else {
                    selectedIndex = position
                    listener.onSelect(position, true)
                    notifyDataSetChanged()
                }
            }
        }

        private fun changeColorToSelected() {
            txtName?.setTextColor(Color.WHITE)
            txtPrice?.setTextColor(Color.WHITE)
            constLayout?.setBackgroundColor(Color.rgb(255, 87, 34))
        }

        private fun changeColorToDefault() {
            txtName?.setTextColor(Color.BLACK)
            txtPrice?.setTextColor(Color.BLACK)
            constLayout?.setBackgroundColor(Color.WHITE)
        }
    }


}
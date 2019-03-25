package br.com.go5.sharedlist.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.data.viewmodel.ProductViewModel
import br.com.go5.sharedlist.ui.activity.MainActivity
import br.com.go5.sharedlist.ui.adapter.ProductAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import kotlinx.android.synthetic.main.fragment_products.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment(), ProductAdapter.OnItemSelected {

    private lateinit var selectedProduct: Product

    override fun onSelect(position: Int, showMenu: Boolean) {
        if (showMenu) {
            selectedProduct = products[position]
            cardOptions.visibility = View.VISIBLE
        } else {
            cardOptions.visibility = View.GONE
        }
    }

    private lateinit var adapter: ProductAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel by viewModel<ProductViewModel>()
    private var products: List<Product> = emptyList()
    private lateinit var recyclerView: RecyclerView
    private var count: Long = 200

    companion object {
        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        btnDelete.setOnClickListener {
            viewModel.delete(selectedProduct)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)

        setupUi(view)

        viewModel.findAll().observe(this, Observer {
            products = it
            adapter.loadItems(products)
            adapter.notifyDataSetChanged()
        })

        return view
    }

    private fun setupUi(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        adapter = ProductAdapter(products, this)
        recyclerView.adapter = adapter

        setFragmentTitle()
    }

    private fun setFragmentTitle() {
        val mainActivity = activity as MainActivity
        val title = mainActivity.getString(R.string.fragment_title_products)
        mainActivity.actionBar.title = title
    }

}

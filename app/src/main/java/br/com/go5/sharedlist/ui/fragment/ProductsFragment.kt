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
import br.com.go5.sharedlist.data.model.Category
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.data.viewmodel.ProductViewModel
import br.com.go5.sharedlist.persistence.UserInfo
import br.com.go5.sharedlist.ui.activity.MainActivity
import br.com.go5.sharedlist.ui.adapter.ProductAdapter
import br.com.go5.sharedlist.ui.adapter.ProductQuickAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.fragment_products.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Arrays.asList
import org.angmarch.views.NiceSpinner
import java.util.*


class ProductsFragment : Fragment(), ProductAdapter.OnItemSelected {

    private lateinit var selectedProduct: Product
    private lateinit var categories: List<Category>
    private lateinit var categoriesNames: MutableList<String>
    private var count: Long = 55L

    override fun onSelect(position: Int, showMenu: Boolean) {
        if (showMenu) {
            selectedProduct = products[position]
            cardOptions.visibility = View.VISIBLE
        } else {
            cardOptions.visibility = View.GONE
        }
    }

    private lateinit var adapter: ProductQuickAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel by viewModel<ProductViewModel>()
    private var products: List<Product> = emptyList()
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance(): ProductsFragment {
            return ProductsFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesNames = mutableListOf()
        setupListeners()
    }

    private fun setupListeners() {
        fabAdd.setOnClickListener {
            MaterialDialog(activity!!).show {
                customView(R.layout.layout_create_product)
                val niceSpinner = this.getCustomView().findViewById(R.id.categorySpinner) as NiceSpinner
                val dataset = LinkedList(categoriesNames)
                if (dataset.size > 0) {
                    niceSpinner.attachDataSource<String>(dataset)
                }
                title(text = "Criar Produto")
                positiveButton {
                    val view = it.getCustomView()
                    val productNameInput: TextInputEditText = view.findViewById(R.id.txtProductName)
                    val productPriceInput: TextInputEditText = view.findViewById(R.id.txtPrice)
                    val niceSpinner = this.getCustomView().findViewById(R.id.categorySpinner) as NiceSpinner
                    niceSpinner.selectedIndex
                    createProduct((Product(count++, productNameInput.text.toString(),
                        productPriceInput.text.toString().toDouble(), categories[niceSpinner.selectedIndex].id)))
                }
            }
        }
    }

    private fun createProduct(product: Product) {
        viewModel.addProduct(product).observe(this, Observer<Product> {
            if (it != null){
                print(it)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)
        getCategories()
        setupUi(view)

        viewModel.findAll().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.setNewData(it)
            }
        })

        return view
    }

    private fun getCategories() {
        viewModel.getAllCategories().observe(this, Observer<List<Category>> {
            if (!it.isNullOrEmpty()){
                categories = it
                extractCategoryNames(categories)
            }
        })
    }

    private fun extractCategoryNames(categories: List<Category>) {
        val names: MutableList<String> = mutableListOf()
        categories.forEach {
            names.add(it.name)
        }
        categoriesNames = names
    }

    private fun setupUi(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        adapter = ProductQuickAdapter(products, false)
        recyclerView.adapter = adapter

        setFragmentTitle()
    }

    private fun setFragmentTitle() {
        val mainActivity = activity as MainActivity
        val title = mainActivity.getString(R.string.fragment_title_products)
        mainActivity.actionBar.title = title
    }

}

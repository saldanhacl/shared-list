package br.com.go5.sharedlist.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Category
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.data.model.ShoppingList
import br.com.go5.sharedlist.data.viewmodel.CategoryViewModel
import br.com.go5.sharedlist.data.viewmodel.ShoppingListViewModel
import br.com.go5.sharedlist.data.viewmodel.ViewModelFactory
import br.com.go5.sharedlist.ui.adapter.CategoryQuickAdapter
import br.com.go5.sharedlist.ui.adapter.ProductQuickAdapter
import kotlinx.android.synthetic.main.activity_add_product.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class AddProductActivity : AppCompatActivity() {

    private lateinit var shoppingList: ShoppingList
    private lateinit var viewModel: CategoryViewModel
    private lateinit var shoppingListViewModel: ShoppingListViewModel
    private lateinit var categoriesAdapter: CategoryQuickAdapter
    private var categories: List<Category> = emptyList()

    companion object {
        var selectedProducts: MutableList<Product> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        supportActionBar?.title = "Lista"
        shoppingList = intent.getSerializableExtra("shoppingList") as ShoppingList
        setupUi()
        viewModel = createViewModel()
        shoppingListViewModel = createShoppingListViewModel()
        setupListeners()
        getCategories()
    }

    private fun setupListeners() {
        var count = 0
        fabAddSelectedItems.setOnClickListener {
            selectedProducts.forEach {
                count++
                shoppingListViewModel.insertProductToList(shoppingList, it).observe(this, Observer<ShoppingList> {
                    if (count == selectedProducts.lastIndex + 1) {
                        startActivity(intentFor<ShoppingListDetailActivity>(
                            "shoppingList" to shoppingList
                        ).clearTop())
                    }
                })
            }
        }
    }

    private fun getCategories() {
        viewModel.get().observe(this, Observer<List<Category>> {
            if (!it.isNullOrEmpty()){
                categories = it
                categoriesAdapter.setNewData(it)
            }
            contentLayout.visibility = View.VISIBLE
            spinner.hide()
        })
    }

    private fun setupUi() {
        val inflater = LayoutInflater.from(this)
        val emptyView = inflater.inflate(R.layout.empty_list, null)
        rvCategories.layoutManager = LinearLayoutManager(this)
        categoriesAdapter = CategoryQuickAdapter(categories)
        categoriesAdapter.emptyView = emptyView
        rvCategories.adapter = categoriesAdapter
    }
    private fun createViewModel(): CategoryViewModel {
        val factory = ViewModelFactory()
        return ViewModelProviders.of(this, factory).get(CategoryViewModel::class.java)
    }

    private fun createShoppingListViewModel(): ShoppingListViewModel {
        val factory = ViewModelFactory()
        return ViewModelProviders.of(this, factory).get(ShoppingListViewModel::class.java)
    }
}

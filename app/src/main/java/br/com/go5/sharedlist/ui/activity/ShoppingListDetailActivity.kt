package br.com.go5.sharedlist.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Category
import br.com.go5.sharedlist.data.model.ShoppingList
import br.com.go5.sharedlist.ui.adapter.CategoryQuickAdapter
import kotlinx.android.synthetic.main.activity_shopping_list_detail.*
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.go5.sharedlist.data.viewmodel.CategoryViewModel
import br.com.go5.sharedlist.data.viewmodel.GroupViewModel
import br.com.go5.sharedlist.data.viewmodel.ViewModelFactory
import br.com.go5.sharedlist.ui.adapter.GroupQuickAdapter


class ShoppingListDetailActivity : AppCompatActivity() {

    private lateinit var shoppingList: ShoppingList
    private lateinit var viewModel: CategoryViewModel
    private lateinit var categoriesAdapter: CategoryQuickAdapter
    private var categories: List<Category> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list_detail)

        setupUi()
    }

    private fun setupUi() {
        val inflater = LayoutInflater.from(this)
        val emptyView = inflater.inflate(R.layout.empty_list, null)
        shoppingList = intent.getSerializableExtra("shoppingList") as ShoppingList
        setupUi(emptyView)
        viewModel = createViewModel()
        getCategories()
    }

    private fun createViewModel(): CategoryViewModel {
        val factory = ViewModelFactory()
        return ViewModelProviders.of(this, factory).get(CategoryViewModel::class.java)
    }

    private  fun getCategories() {
        viewModel.get().observe(this, Observer<List<Category>> {
            if (!it.isNullOrEmpty()){
                categories = it
                categoriesAdapter.setNewData(it)
            }
            contentLayout.visibility = View.VISIBLE
            spinner.hide()
        })
    }

    private fun setupUi(emptyView: View) {
        rvCategories.layoutManager = LinearLayoutManager(this)
        categoriesAdapter = CategoryQuickAdapter(categories)
        categoriesAdapter.emptyView = emptyView
        rvCategories.adapter = categoriesAdapter

        txtShoppingListName.text = shoppingList.name
    }
}

package br.com.go5.sharedlist.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.ShoppingList
import kotlinx.android.synthetic.main.activity_shopping_list_detail.*
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.go5.sharedlist.data.model.Comment
import br.com.go5.sharedlist.data.model.CommentResponse
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.data.viewmodel.ShoppingListViewModel
import br.com.go5.sharedlist.data.viewmodel.ViewModelFactory
import br.com.go5.sharedlist.persistence.UserInfo
import br.com.go5.sharedlist.ui.adapter.CommentQuickAdapter
import br.com.go5.sharedlist.ui.adapter.ProductFromListQuickAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.getbase.floatingactionbutton.FloatingActionButton
import com.getbase.floatingactionbutton.FloatingActionsMenu
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast


class ShoppingListDetailActivity : AppCompatActivity() {

    private lateinit var shoppingList: ShoppingList
    private lateinit var viewModel: ShoppingListViewModel
    private lateinit var productsAdapter: ProductFromListQuickAdapter
    private lateinit var commentsAdapter: CommentQuickAdapter

    private lateinit var fabAdd: FloatingActionsMenu
    private lateinit var fabAddProduct: FloatingActionButton
    private lateinit var fabAddComment: FloatingActionButton


    private var products: MutableList<Product> = mutableListOf()
    private var comments: MutableList<CommentResponse> = mutableListOf()

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
        getProducts()
        getComments()
        setupListeners()
    }
    private fun setupListeners() {
//        btnAddProduct.setOnClickListener {
//            startActivity(intentFor<AddProductActivity>("shoppingList" to shoppingList))
//        }

        fabAddProduct.setOnClickListener {
            startActivity(intentFor<AddProductActivity>("shoppingList" to shoppingList))
        }
        fabAddComment.setOnClickListener {
            MaterialDialog(this@ShoppingListDetailActivity).show {
                customView(R.layout.layout_create_comment)
                title(R.string.create_comment_title)
                positiveButton {
                    val view = it.getCustomView()
                    val commentInput: TextInputLayout = view.findViewById(R.id.txtComment)
                    createComment(commentInput.editText?.text.toString())
                }
            }
        }
    }

    private fun createComment(comment: String) {
        viewModel.createComment(shoppingList, comment, UserInfo.id).observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                commentsAdapter.setNewData(it)
            }
        })
    }

    private fun createViewModel(): ShoppingListViewModel {
        val factory = ViewModelFactory()
        return ViewModelProviders.of(this, factory).get(ShoppingListViewModel::class.java)
    }

    private fun getProducts() {
        viewModel.getProducts(shoppingList).observe(this, Observer<MutableList<Product>> {
            if (!it.isNullOrEmpty()){
                products = it
                productsAdapter.setNewData(it)
            }
            contentLayout.visibility = View.VISIBLE
            spinner.hide()
        })
    }

    private fun getComments() {
        viewModel.getComments(shoppingList).observe(this, Observer<MutableList<CommentResponse>> {
            if (!it.isNullOrEmpty()){
                comments = it
                commentsAdapter.setNewData(it)
            }
            contentLayout.visibility = View.VISIBLE
            spinner.hide()
        })
    }

    private fun setupUi(emptyView: View) {
        fabAdd = findViewById(R.id.fabAdd)
        fabAddProduct = findViewById(R.id.fabAddProduct)
        fabAddComment = findViewById(R.id.fabAddComment)

        rvProducts.layoutManager = LinearLayoutManager(this)
        productsAdapter = ProductFromListQuickAdapter(products, this)
//        productsAdapter.emptyView = emptyView
        rvProducts.adapter = productsAdapter

        rvComments.layoutManager = LinearLayoutManager(this)
        commentsAdapter = CommentQuickAdapter(comments)
        rvComments.adapter = commentsAdapter

        txtShoppingListName.text = shoppingList.name
    }

    fun deleteProductFromList(product: Product) {
        viewModel.deleteProductFromList(shoppingList, product).observe(this, Observer<ShoppingList> {
            productsAdapter.notifyDataSetChanged()
        })
    }
}

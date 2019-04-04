package br.com.go5.sharedlist.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.data.model.ShoppingList
import br.com.go5.sharedlist.data.viewmodel.ShoppingListViewModel
import br.com.go5.sharedlist.network.ShoppingListService
import br.com.go5.sharedlist.network.RetrofitInit
import br.com.go5.sharedlist.persistence.UserInfo
import br.com.go5.sharedlist.ui.activity.MainActivity
import br.com.go5.sharedlist.ui.adapter.ShoppingListAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import javax.security.auth.callback.Callback

class ShoppingListFragment : Fragment(), ShoppingListAdapter.OnItemSelected  {

    private lateinit var adapter: ShoppingListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel by viewModel<ShoppingListViewModel>()
    var shoppingLists: List<ShoppingList> = emptyList()
    private lateinit var recyclerView: RecyclerView
    private val retrofit: RetrofitInit by inject()
    private var count: Long = 55L

    private lateinit var selectedShoppingList: ShoppingList


    override fun onSelect(position: Int, showMenu: Boolean) {
        if (showMenu) {
            selectedShoppingList = shoppingLists[position]
            cardOptions.visibility = View.VISIBLE
        } else {
            cardOptions.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        setFragmentTitle()
        setupUi(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch { getShoppingLists() }
        setupListeners()
    }

    private suspend fun getShoppingLists() {
        spinner.show()
        try {
//            val responseShoppingLists = viewModel.findAllByUser(UserInfo.id)
//            if (!responseShoppingLists.isNullOrEmpty()) {
//                shoppingLists = responseShoppingLists
//                adapter.loadItems(shoppingLists)
//            }
            adapter.loadItems(listOf(ShoppingList(123, "Compras do mês"), ShoppingList(1234, "Compras da semana")))
        } catch (exception: HttpException) {
            showAlertDialog(getString(R.string.server_error))
        } catch (exception: Exception) {
            showAlertDialog(getString(R.string.server_error))
        } finally {
            hideSpinner()
        }
    }

    private fun hideSpinner() {
        activity?.runOnUiThread {
            spinner.hide()
        }
    }

    private fun setupUi(view: View) {
        recyclerView = view.findViewById(R.id.rvShoppingLists)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        adapter = ShoppingListAdapter(shoppingLists, this)
        recyclerView.adapter = adapter

        setFragmentTitle()
    }

    private fun setupListeners() {
        btnDelete.setOnClickListener {
            viewModel.delete(selectedShoppingList)
        }
        fabAdd.setOnClickListener {
            MaterialDialog(activity!!).show {
                customView(R.layout.layout_create_shopping_list)
                title(R.string.create_shoppingList_card_title)
                positiveButton {
                    val view = it.getCustomView()
                    val shoppingListNameInput: TextInputLayout = view.findViewById(R.id.txtShoppingListName)
                    adapter.loadItems(listOf(
                        ShoppingList(count++, shoppingListNameInput.editText?.text.toString())
                    ))
                    adapter.notifyDataSetChanged()
//                    createShoppingList(shoppingListNameInput.editText?.text.toString(), UserInfo.id)
                }
            }
        }
    }

    private fun createShoppingList(shoppingListName: String, creator: Long) {
        GlobalScope.launch {
            try {
                viewModel.createInServer(shoppingListName, creator)
            } catch (exception: HttpException) {
                showAlertDialog(getString(R.string.server_error))
            } catch (exception: Exception) {
                showAlertDialog(getString(R.string.server_error))
            } finally {
                getShoppingLists()
            }
        }
    }

    private fun showAlertDialog(message: String) {
        MaterialDialog(activity!!).show {
            title(R.string.alert)
            message(text = message)
            positiveButton(R.string.agree)
        }
    }

    private fun setFragmentTitle() {
        val mainActivity = activity as MainActivity
        val title = mainActivity.getString(R.string.fragment_title_shoppingLists)
        mainActivity.actionBar.title = title
    }

}

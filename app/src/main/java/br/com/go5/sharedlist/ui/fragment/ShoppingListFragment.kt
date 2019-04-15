package br.com.go5.sharedlist.ui.fragment

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.ShoppingList
import br.com.go5.sharedlist.data.viewmodel.ShoppingListViewModel
import br.com.go5.sharedlist.data.viewmodel.ViewModelFactory
import br.com.go5.sharedlist.ui.activity.MainActivity
import br.com.go5.sharedlist.ui.activity.ShoppingListDetailActivity
import br.com.go5.sharedlist.ui.adapter.ShoppingListQuickAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import org.jetbrains.anko.intentFor

class ShoppingListsFragment : Fragment()  {

    private lateinit var viewModel: ShoppingListViewModel
    private lateinit var adapter: ShoppingListQuickAdapter
    private lateinit var recyclerView: RecyclerView
    private var shoppingLists: List<ShoppingList> = emptyList()

    companion object {
        fun newInstance(): ShoppingListsFragment {
            return ShoppingListsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        val emptyView = inflater.inflate(R.layout.empty_list, container, false)
        val mainActivity = activity as MainActivity
        shoppingLists = mainActivity.selectedGroup!!.listasDeCompras
        setFragmentTitle()
        setupUi(view,emptyView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = createViewModel()
        setupListeners()
    }

    private fun createViewModel(): ShoppingListViewModel {
        val factory = ViewModelFactory()
        return ViewModelProviders.of(this, factory).get(ShoppingListViewModel::class.java)
    }

    private fun setupUi(view: View, emptyView: View) {
        recyclerView = view.findViewById(R.id.rvShoppingLists)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        setupAdapter(emptyView)
        recyclerView.adapter = adapter
        setFragmentTitle()
    }

    private fun setupAdapter(emptyView: View) {
        adapter = ShoppingListQuickAdapter(shoppingLists)
        adapter.emptyView = emptyView
        val mItemDragAndSwipeCallback = ItemDragAndSwipeCallback(adapter)
        val mItemTouchHelper = ItemTouchHelper(mItemDragAndSwipeCallback)
        mItemTouchHelper.attachToRecyclerView(recyclerView)

        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)
        adapter.enableSwipeItem()
        adapter.setOnItemSwipeListener(onItemSwipeListener)
        adapter.onItemClickListener = onCLickListener
    }

    private fun setupListeners() {
        fabAdd.setOnClickListener {
            MaterialDialog(activity!!).show {
                customView(R.layout.layout_create_shopping_list)
                title(R.string.create_shoppingList_card_title)
                positiveButton {
                    val view = it.getCustomView()
                    val shoppingListNameInput: TextInputLayout = view.findViewById(R.id.txtShoppingListName)
                    createShoppingList(shoppingListNameInput.editText?.text.toString())
                }
            }
        }
    }

    private fun createShoppingList(shoppingListName: String) {
        val activity = activity as MainActivity
        viewModel.createInServer(shoppingListName, activity.selectedGroup!!.id).observe(activity, Observer {
            if (it == null) {
                showAlertDialog(getString(R.string.server_error))
            } else {
                viewModel.getGroupById(activity.selectedGroup!!.id).observe(activity, Observer {
                    shoppingLists = it.listasDeCompras
                    adapter.setNewData(shoppingLists)
                })
            }
        })
    }

    private fun showAlertDialog(message: String) {
        activity?.runOnUiThread {
            MaterialDialog(activity!!).show {
                title(R.string.alert)
                message(text = message)
                positiveButton(R.string.agree)
            }
        }
    }

    private fun setFragmentTitle() {
        val mainActivity = activity as MainActivity
        val title = mainActivity.getString(R.string.fragment_title_shoppingLists)
        mainActivity.actionBar.title = title
    }


    val onItemSwipeListener = object : OnItemSwipeListener {
        override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder, pos: Int) {
            print(pos)
        }
        override fun clearView(viewHolder: RecyclerView.ViewHolder, pos: Int) {
            print(pos)
        }
        override fun onItemSwipeMoving(canvas: Canvas, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, isCurrentlyActive: Boolean) {
            canvas.drawColor(ContextCompat.getColor(activity!!, R.color.colorAccent))
        }

        override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, pos: Int) {
            if (pos != -1) {
                viewModel.delete(shoppingLists[pos]).observe(activity!!, Observer {
                    print(it)
                })
            }
        }

    }

    val onCLickListener = object : BaseQuickAdapter.OnItemClickListener {
        override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            startActivity(activity?.intentFor<ShoppingListDetailActivity>(
                "shoppingList" to shoppingLists[position]
            ))
        }
    }

}

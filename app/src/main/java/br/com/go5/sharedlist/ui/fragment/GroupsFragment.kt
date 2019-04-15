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
import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.viewmodel.GroupViewModel
import br.com.go5.sharedlist.data.viewmodel.ViewModelFactory
import br.com.go5.sharedlist.persistence.UserInfo
import br.com.go5.sharedlist.ui.activity.MainActivity
import br.com.go5.sharedlist.ui.adapter.GroupQuickAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_groups.*

class GroupsFragment : Fragment()  {

    private lateinit var viewModel: GroupViewModel
    private lateinit var adapter: GroupQuickAdapter
    private lateinit var recyclerView: RecyclerView
    private var groups: List<Group> = emptyList()

    companion object {
        fun newInstance(): GroupsFragment {
            return GroupsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_groups, container, false)
        val emptyView = inflater.inflate(R.layout.empty_list, container, false)
        setFragmentTitle()
        setupUi(view,emptyView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = createViewModel()
        getGroups()
        setupListeners()
    }

    private fun createViewModel(): GroupViewModel {
        val factory = ViewModelFactory()
        return ViewModelProviders.of(this, factory).get(GroupViewModel::class.java)
    }


    private  fun getGroups() {
        viewModel.findAllByUser(UserInfo.id)?.observe(this, Observer<List<Group>> {
            if (!it.isNullOrEmpty()){
                groups = it
                adapter.setNewData(it)
            }
            recyclerView.visibility = View.VISIBLE
            hideSpinner()
        })
    }

    private fun hideSpinner() {
        activity?.runOnUiThread {
            spinner.hide()
        }
    }

    private fun setupUi(view: View, emptyView: View) {
        recyclerView = view.findViewById(R.id.rvGroups)
        recyclerView.visibility = View.INVISIBLE
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        setupAdapter(emptyView)
        recyclerView.adapter = adapter
        setFragmentTitle()
    }

    private fun setupAdapter(emptyView: View) {
        adapter = GroupQuickAdapter(groups)
        adapter.emptyView = emptyView

        val mItemDragAndSwipeCallback = ItemDragAndSwipeCallback(adapter)
        val mItemTouchHelper = ItemTouchHelper(mItemDragAndSwipeCallback)
        mItemTouchHelper.attachToRecyclerView(recyclerView)

        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)
        adapter.enableSwipeItem()
        adapter.setOnItemSwipeListener(onItemSwipeListener)

        adapter.onItemClickListener = onClickListener
    }

    private fun setupListeners() {
        fabAdd.setOnClickListener {
            MaterialDialog(activity!!).show {
                customView(R.layout.layout_create_group)
                title(R.string.create_group_card_title)
                positiveButton {
                    val view = it.getCustomView()
                    val groupNameInput: TextInputLayout = view.findViewById(R.id.txtShoppingListName)
                    createGroup(groupNameInput.editText?.text.toString(), UserInfo.id)
                }
            }
        }
    }

    private fun createGroup(groupName: String, creator: Long) {
        viewModel.createInServer(groupName, creator).observe(activity!!, Observer {
            if (it == null) {
                showAlertDialog(getString(R.string.server_error))
            }
            getGroups()
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
        val title = mainActivity.getString(R.string.fragment_title_groups)
        mainActivity.actionBar.title = title
    }

    // LISTENERS

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
                viewModel.delete(groups[pos]).observe(activity!!, Observer {
                    print(it)
                })
            }
        }
    }

    val onClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        val activity = activity as MainActivity
        activity.selectedGroup = groups[position]
        val shoppingListFragment = ShoppingListsFragment.newInstance()
        activity.openFragment(shoppingListFragment)
    }

}

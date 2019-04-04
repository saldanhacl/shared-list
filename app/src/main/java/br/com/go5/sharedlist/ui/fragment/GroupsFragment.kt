package br.com.go5.sharedlist.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.viewmodel.GroupViewModel
import br.com.go5.sharedlist.network.RetrofitInit
import br.com.go5.sharedlist.persistence.UserInfo
import br.com.go5.sharedlist.ui.activity.MainActivity
import br.com.go5.sharedlist.ui.adapter.GroupAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_groups.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException

class GroupsFragment : Fragment(), GroupAdapter.OnItemSelected  {

    private lateinit var adapter: GroupAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModel by viewModel<GroupViewModel>()
    var groups: List<Group> = emptyList()
    private lateinit var recyclerView: RecyclerView
    private val retrofit: RetrofitInit by inject()

    private lateinit var selectedGroup: Group


    override fun onSelect(position: Int) {
        val shoppingListFragment = ShoppingListFragment.newInstance()
        (activity as MainActivity).openFragment(shoppingListFragment)
    }

    companion object {
        fun newInstance(): GroupsFragment {
            return GroupsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_groups, container, false)
        setFragmentTitle()
        setupUi(view)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch { getGroups() }
        setupListeners()
    }

    private suspend fun getGroups() {
        activity?.runOnUiThread {
            spinner.show()
        }
        try {
            val responseGroups = viewModel.findAllByUser(UserInfo.id)
            if (!responseGroups.isNullOrEmpty()) {
                groups = responseGroups
                adapter.loadItems(groups)
            }
        } catch (exception: HttpException) {
            activity?.runOnUiThread {
                showAlertDialog(getString(R.string.server_error))
            }
        } catch (exception: Exception) {
            activity?.runOnUiThread {
                showAlertDialog(getString(R.string.server_error))
            }
        } finally {
//            adapter.loadItems(listOf(Group(1, "Faculdade"), Group(2, "Casa")))
            hideSpinner()
        }
    }

    private fun hideSpinner() {
        activity?.runOnUiThread {
            spinner.hide()
        }
    }

    private fun setupUi(view: View) {
        recyclerView = view.findViewById(R.id.rvGroups)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
        adapter = GroupAdapter(groups, this)
        recyclerView.adapter = adapter

        setFragmentTitle()
    }

    private fun setupListeners() {
        btnDelete.setOnClickListener {
            viewModel.delete(selectedGroup)
        }
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
        GlobalScope.launch {
            try {
                viewModel.createInServer(groupName, creator)
            } catch (exception: HttpException) {
                activity?.runOnUiThread {
                    showAlertDialog(getString(R.string.server_error))
                }
            } catch (exception: Exception) {
                activity?.runOnUiThread {
                    showAlertDialog(getString(R.string.server_error))
                }
            } finally {
                getGroups()
            }
        }
    }

    private fun showAlertDialog(message: String) {
//        MaterialDialog(activity!!).show {
//            title(R.string.alert)
//            message(text = message)
//            positiveButton(R.string.agree)
//        }
    }

    private fun setFragmentTitle() {
        val mainActivity = activity as MainActivity
        val title = mainActivity.getString(R.string.fragment_title_groups)
        mainActivity.actionBar.title = title
    }

}

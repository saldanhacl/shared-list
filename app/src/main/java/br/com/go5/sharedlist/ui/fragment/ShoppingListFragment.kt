package br.com.go5.sharedlist.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.ui.activity.MainActivity

class ShoppingListFragment : Fragment() {

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        setupUI()
        return view
    }

    private fun setupUI() {
        setFragmentTitle()
    }

    private fun setFragmentTitle() {
        val mainActivity = activity as MainActivity
        val title = mainActivity.getString(R.string.fragment_title_shopping_list)
        mainActivity.actionBar.title = title
    }

}

package br.com.go5.sharedlist.ui.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.ui.fragment.ProductsFragment
import br.com.go5.sharedlist.ui.fragment.SettingsFragment
import br.com.go5.sharedlist.ui.fragment.ShoppingListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var actionBar: ActionBar

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navShoppingList -> {
                val shoppingListFragment = ShoppingListFragment.newInstance()
                openFragment(shoppingListFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navProducts -> {
                val productListFragment = ProductsFragment.newInstance()
                openFragment(productListFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navSettings -> {
                val settingsFragment = SettingsFragment.newInstance()
                openFragment(settingsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI () {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val shoppingListFragment = ShoppingListFragment.newInstance()
        openFragment(shoppingListFragment)
        actionBar = supportActionBar!!
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}

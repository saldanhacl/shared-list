package br.com.go5.sharedlist.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(GroupViewModel::class.java) ->
                    GroupViewModel()
                isAssignableFrom(ShoppingListViewModel::class.java) ->
                    ShoppingListViewModel()
                isAssignableFrom(CategoryViewModel::class.java) ->
                    CategoryViewModel()
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel()
                else ->
                    throw IllegalArgumentException("Classe desconhecida.")
            }
        } as T
}
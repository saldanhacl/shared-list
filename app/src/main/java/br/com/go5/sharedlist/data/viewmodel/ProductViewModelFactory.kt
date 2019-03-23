package br.com.go5.sharedlist.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.go5.sharedlist.data.repository.ProductRepository
import br.com.go5.sharedlist.di.SharedListApplication

class ProductViewModelFactory (): ViewModelProvider.NewInstanceFactory() {
//    private val app: SharedListApplication by kodein.instance()

//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return ProductViewModel(app, kodein) as T
//    }
}
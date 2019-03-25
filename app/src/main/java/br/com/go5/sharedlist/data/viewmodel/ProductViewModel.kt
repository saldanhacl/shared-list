package br.com.go5.sharedlist.data.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.data.repository.ProductRepository
import br.com.go5.sharedlist.di.SharedListApplication
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ProductViewModel: ViewModel(), KoinComponent {

    private val productRepository: ProductRepository by inject()
    private var products: LiveData<List<Product>>

    init {
        products = productRepository.findAll()
    }

    fun delete(product: Product) {
        productRepository.delete(product)
    }

    fun insert(product: Product) {
        productRepository.insert(product)
    }

    fun findAll(): LiveData<List<Product>> {
        return this.products
    }


}
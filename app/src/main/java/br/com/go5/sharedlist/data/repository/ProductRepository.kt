package br.com.go5.sharedlist.data.repository

import androidx.lifecycle.LiveData
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.persistence.dao.ProductDao
import br.com.go5.sharedlist.utils.Utils
import org.jetbrains.anko.doAsync
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class ProductRepository: KoinComponent {

    private val productDao: ProductDao by inject()
    private val utils: Utils by inject()

    fun insert(product: Product) {
        doAsync {
            productDao.insert(product)
        }
    }

    fun findAll(): LiveData<List<Product>> {
        var products: LiveData<List<Product>>? = null
        if (utils.isConnectedToInternet()) {
//             fetch from api
            products = productDao.findAll()
        } else {
        }
        return products!!
    }
}
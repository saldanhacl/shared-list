package br.com.go5.sharedlist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.model.Product
import br.com.go5.sharedlist.network.RetrofitInit
import br.com.go5.sharedlist.persistence.UserInfo
import br.com.go5.sharedlist.persistence.dao.ProductDao
import br.com.go5.sharedlist.utils.Utils
import org.jetbrains.anko.doAsync
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductRepository: KoinComponent {

    private val retrofit: RetrofitInit by inject()
    private val productDao: ProductDao by inject()
    private val utils: Utils by inject()

    fun insert(product: Product) {
        doAsync {
            productDao.insert(product)
        }
    }

    fun delete(product: Product) {
        doAsync {
            productDao.delete(product)
        }
    }
    fun findAll(): LiveData<List<Product>> {
        var products: LiveData<List<Product>>?
        products = productDao.findAll()
        return products!!
    }

    fun addProduct(product: Product): MutableLiveData<Product> {
        insert(product)
        val data = MutableLiveData<Product>()
        if (utils.isConnectedToInternet()) {
            retrofit.productService().create(product.name, product.price, product.categoryId, UserInfo.id).enqueue(
                object : Callback<Product> {
                    override fun onFailure(call: Call<Product>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<Product>, response: Response<Product>) {
                        data.value = response.body()
                    }

                }
            )
        }
        return data
    }
}
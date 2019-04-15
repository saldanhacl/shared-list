package br.com.go5.sharedlist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.go5.sharedlist.data.model.Category
import br.com.go5.sharedlist.network.RetrofitInit
//import br.com.go5.sharedlist.persistence.dao.CategoryDao
import br.com.go5.sharedlist.utils.Utils
import org.jetbrains.anko.doAsync
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class CategoryRepository: KoinComponent {

    private val retrofit: RetrofitInit by inject()
    private val utils: Utils by inject()

    fun getAll(): LiveData<List<Category>> {
        val data = MutableLiveData<List<Category>>()
        if (utils.isConnectedToInternet()) {
            retrofit.shoppingListService().getCategories().enqueue(
                object: Callback<List<Category>> {
                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        data.value = null
                    }
                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                        data.value = response.body()
                    }
                }
            )
        }
        return data
    }
}
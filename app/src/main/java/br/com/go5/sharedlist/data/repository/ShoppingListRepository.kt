package br.com.go5.sharedlist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.go5.sharedlist.data.model.*
import br.com.go5.sharedlist.network.RetrofitInit
import br.com.go5.sharedlist.persistence.dao.ShoppingListDao
import br.com.go5.sharedlist.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class ShoppingListRepository: KoinComponent {

    private val shoppingListDao: ShoppingListDao by inject()
    private val retrofit: RetrofitInit by inject()
    private val utils: Utils by inject()

    fun insert(shoppingList: ShoppingList) {
        doAsync {
            shoppingListDao.insert(shoppingList)
        }
    }

    fun createInServer(shoppingListName: String, groupId: Long): MutableLiveData<ShoppingList> {
        val data = MutableLiveData<ShoppingList>()
        if (utils.isConnectedToInternet()) {
            retrofit.shoppingListService().create(shoppingListName, groupId).enqueue(
                object : Callback<ShoppingList> {
                    override fun onFailure(call: Call<ShoppingList>, t: Throwable) {
                        data.value = null
                    }
                    override fun onResponse(call: Call<ShoppingList>, response: Response<ShoppingList>) {
                        data.value = response.body()
                    }

                }
            )
        }
        return data
    }

    fun createComment(shoppingList: ShoppingList, comment: String, userId: Long): MutableLiveData<MutableList<CommentResponse>> {
        val data = MutableLiveData<MutableList<CommentResponse>>()
        if (utils.isConnectedToInternet()) {
            retrofit.shoppingListService().createComment(shoppingList.id, comment, userId).enqueue(
                object : Callback<MutableList<CommentResponse>> {
                    override fun onFailure(call: Call<MutableList<CommentResponse>>, t: Throwable) {
                        data.value = null
                    }
                    override fun onResponse(call: Call<MutableList<CommentResponse>>, response: Response<MutableList<CommentResponse>>) {
                        data.value = response.body()
                    }

                }
            )
        }
        return data
    }

    fun delete(shoppingList: ShoppingList): LiveData<ShoppingList> {
        val data = MutableLiveData<ShoppingList>()
        if (utils.isConnectedToInternet()) {
            retrofit.shoppingListService().delete(listId = shoppingList.id).enqueue(
                object: Callback<ShoppingList> {
                    override fun onFailure(call: Call<ShoppingList>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<ShoppingList>, response: Response<ShoppingList>) {
                        data.value = response.body()
                    }
                }
            )
        }
        return data
    }

    fun getProducts(shoppingList: ShoppingList): LiveData<MutableList<Product>> {
        val data = MutableLiveData<MutableList<Product>>()
        if (utils.isConnectedToInternet()) {
            retrofit.shoppingListService().getProductsFromList(listId = shoppingList.id).enqueue(
                object: Callback<MutableList<Product>> {
                    override fun onFailure(call: Call<MutableList<Product>>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<MutableList<Product>>, response: Response<MutableList<Product>>) {
                        data.value = response.body()
                    }
                }
            )
        }
        return data
    }

    fun getComments(shoppingList: ShoppingList): LiveData<MutableList<CommentResponse>> {
        val data = MutableLiveData<MutableList<CommentResponse>>()
        if (utils.isConnectedToInternet()) {
            retrofit.shoppingListService().getCommentsFromList(listId = shoppingList.id).enqueue(
                object: Callback<MutableList<CommentResponse>> {
                    override fun onFailure(call: Call<MutableList<CommentResponse>>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<MutableList<CommentResponse>>, response: Response<MutableList<CommentResponse>>) {
                        data.value = response.body()
                    }
                }
            )
        }
        return data
    }

    fun insertProductToList(shoppingList: ShoppingList, product: Product): LiveData<ShoppingList> {
        val data = MutableLiveData<ShoppingList>()
        if (utils.isConnectedToInternet()) {
            retrofit.shoppingListService().insertProductToList(
                listId = shoppingList.id,
                productId = product.id
                ).enqueue(
                object: Callback<ShoppingList> {
                    override fun onFailure(call: Call<ShoppingList>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<ShoppingList>, response: Response<ShoppingList>) {
                        data.value = response.body()
                    }
                }
            )
        }
        return data
    }

    fun deleteProductFromList(shoppingList: ShoppingList, product: Product): LiveData<ShoppingList> {
        val data = MutableLiveData<ShoppingList>()
        if (utils.isConnectedToInternet()) {
            retrofit.shoppingListService().deleteProductFromList(
                listId = shoppingList.id,
                productId = product.id
            ).enqueue(
                object: Callback<ShoppingList> {
                    override fun onFailure(call: Call<ShoppingList>, t: Throwable) {
                        data.value = null
                    }

                    override fun onResponse(call: Call<ShoppingList>, response: Response<ShoppingList>) {
                        data.value = response.body()
                    }
                }
            )
        }
        return data
    }
    fun findAll(): LiveData<List<ShoppingList>> {
        var shoppingLists: LiveData<List<ShoppingList>>? = null
        if (utils.isConnectedToInternet()) {

        } else {
        }
        return shoppingLists!!
    }

}
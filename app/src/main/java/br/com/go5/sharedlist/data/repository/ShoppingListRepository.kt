package br.com.go5.sharedlist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.model.ShoppingList
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
    fun findAll(): LiveData<List<ShoppingList>> {
        var shoppingLists: LiveData<List<ShoppingList>>? = null
        if (utils.isConnectedToInternet()) {

        } else {
        }
        return shoppingLists!!
    }

}
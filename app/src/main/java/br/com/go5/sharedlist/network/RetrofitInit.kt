package br.com.go5.sharedlist.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInit {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://will-list.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    fun userService() = retrofit.create(UserService::class.java)
    fun groupService() = retrofit.create(GroupService::class.java)
    fun shoppingListService() = retrofit.create(ShoppingListService::class.java)
}
package br.com.go5.sharedlist.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.xml.datatype.DatatypeConstants.SECONDS
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class RetrofitInit {

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://will-list.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()

    fun productService() = retrofit.create(ProductService::class.java)
    fun userService() = retrofit.create(UserService::class.java)
    fun groupService() = retrofit.create(GroupService::class.java)
    fun shoppingListService() = retrofit.create(ShoppingListService::class.java)
}
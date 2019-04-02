package br.com.go5.sharedlist.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInit {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://will-list.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun productService() = retrofit.create(ProductService::class.java)
    fun userService() = retrofit.create(UserService::class.java)
}
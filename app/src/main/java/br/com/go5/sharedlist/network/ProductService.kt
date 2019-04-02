package br.com.go5.sharedlist.network

import retrofit2.http.GET

interface ProductService {

    @GET("products")
    fun list()
}
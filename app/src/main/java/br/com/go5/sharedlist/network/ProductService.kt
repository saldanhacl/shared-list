package br.com.go5.sharedlist.network

import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    fun list()

    @POST("/products/")
    fun create(
        @Query("nome") name: String,
        @Query("preco") price: Double,
        @Query("idCategoria") categoryId: Long,
        @Query("idUsuario") userId: Long): Call<Product>
}
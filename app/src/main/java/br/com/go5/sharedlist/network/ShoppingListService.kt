package br.com.go5.sharedlist.network

import br.com.go5.sharedlist.data.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface ShoppingListService {

    @DELETE("/lists/{id}/")
    fun delete(@Path("id") listId: Long): Call<ShoppingList>

    @GET("/lists/{id}/products")
    fun getProductsFromList(@Path("id") listId: Long): Call<MutableList<Product>>

    @GET("/lists/{id}/comments")
    fun getCommentsFromList(@Path("id") listId: Long): Call<MutableList<CommentResponse>>

    @PUT("/lists/{id}/products/{productId}")
    fun insertProductToList(
        @Path("id") listId: Long,
        @Path("productId") productId: Long
        ): Call<ShoppingList>

    @DELETE("/lists/{id}/products/{productId}")
    fun deleteProductFromList(
        @Path("id") listId: Long,
        @Path("productId") productId: Long
    ): Call<ShoppingList>

    @POST("/lists/")
    fun create(
        @Query("nome") name: String,
        @Query("groupId") creator: Long): Call<ShoppingList>

    @PUT("/lists/{id}/comments")
    fun createComment
                (@Path("id") listId: Long,
                 @Query("comment") name: String,
                 @Query("userId") creator: Long): Call<MutableList<CommentResponse>>

    @GET("/categories/")
    fun getCategories(): Call<List<Category>>
}
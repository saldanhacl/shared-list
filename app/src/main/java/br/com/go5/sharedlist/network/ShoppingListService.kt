package br.com.go5.sharedlist.network

import br.com.go5.sharedlist.data.model.Category
import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.model.ShoppingList
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface ShoppingListService {

    @DELETE("/lists/{id}/")
    fun delete(@Path("id") listId: Long): Call<ShoppingList>

    @POST("/lists/")
    fun create(
        @Query("nome") name: String,
        @Query("groupId") creator: Long): Call<ShoppingList>

    @GET("/categories/")
    fun getCategories(): Call<List<Category>>
}
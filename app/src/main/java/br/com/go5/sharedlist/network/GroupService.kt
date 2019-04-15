package br.com.go5.sharedlist.network

import br.com.go5.sharedlist.data.model.Group
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface GroupService {

    @DELETE("/groups/{id}/")
    fun delete(@Path("id") groupId: Long): Call<Group>

    @GET("/groups/{id}/")
    fun get(@Path("id") groupId: Long): Call<Group>

    @POST("/groups/")
    fun create(
        @Query("nome") name: String,
        @Query("idCriador") creator: Long): Call<Group>
}
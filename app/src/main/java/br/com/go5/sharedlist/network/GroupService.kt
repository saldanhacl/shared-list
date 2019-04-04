package br.com.go5.sharedlist.network

import br.com.go5.sharedlist.data.model.Group
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GroupService {

    @GET("groups")
    fun list()

    @POST("/groups/")
    fun create(
        @Query("nome") name: String,
        @Query("idCriador") creator: Long): Deferred<List<Group>>
}
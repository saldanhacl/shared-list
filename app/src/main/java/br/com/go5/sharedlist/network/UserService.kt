package br.com.go5.sharedlist.network

import br.com.go5.sharedlist.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("/users/login")
    fun login(
        @Query("email") email: String,
        @Query("senha") password: String): Call<User>
}
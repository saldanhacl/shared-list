package br.com.go5.sharedlist.network

import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface UserService {

    @POST("/users/login")
    fun login(
        @Query("email") email: String,
        @Query("senha") password: String): Deferred<User>

    @GET("/users/{id}/groups")
    fun getGroups(@Path("id") userId: Long): Deferred<List<Group>>


//    @POST("/users/login")
//    fun login(@Body userAuth: UserAuth): Call<User>
}
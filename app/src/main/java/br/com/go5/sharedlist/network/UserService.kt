package br.com.go5.sharedlist.network

import androidx.lifecycle.LiveData
import br.com.go5.sharedlist.data.model.Group
import br.com.go5.sharedlist.data.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("/users/login")
    fun login(
        @Query("email") email: String,
        @Query("senha") password: String,
        @Query("FCMToken") FCMToken: String): Deferred<User>

    @GET("/users/{id}/groups")
    fun getGroups(@Path("id") userId: Long): Call<List<Group>>


//    @POST("/users/login")
//    fun login(@Body userAuth: UserAuth): Call<User>
}
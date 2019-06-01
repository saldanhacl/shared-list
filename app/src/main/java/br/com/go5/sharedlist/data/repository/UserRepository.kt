package br.com.go5.sharedlist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.go5.sharedlist.data.model.ApiResponse
import br.com.go5.sharedlist.data.model.User
import br.com.go5.sharedlist.di.SharedListApplication
import br.com.go5.sharedlist.network.RetrofitInit
import br.com.go5.sharedlist.utils.Utils
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject




class UserRepository: KoinComponent {

    private val retrofit: RetrofitInit by inject()
    private val utils: Utils by inject()

    fun login(email: String, password: String, FCMToken: String): LiveData<ApiResponse> {
        val data = MutableLiveData<ApiResponse>()
        if (utils.isConnectedToInternet()) {
            retrofit.userService().login(email, password, FCMToken).enqueue(
                object: Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        data.value = null
                    }
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful){
                            data.value = ApiResponse(
                                result = response.body() as User,
                                errorMessage = null)
                        } else {
//                            val objError = JSONObject(response.errorBody()?.string())
                            data.value = ApiResponse(
                                result = null,
                                errorMessage = "Erro ao conectar com o servidor")
                        }
                    }
                }
            )
        }
        return data
    }
}
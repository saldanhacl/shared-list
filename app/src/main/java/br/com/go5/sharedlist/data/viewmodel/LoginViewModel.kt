package br.com.go5.sharedlist.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.go5.sharedlist.data.model.ApiResponse
import br.com.go5.sharedlist.data.model.Category
import br.com.go5.sharedlist.data.model.User
import br.com.go5.sharedlist.data.repository.CategoryRepository
import br.com.go5.sharedlist.data.repository.UserRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class LoginViewModel: ViewModel(), KoinComponent {

    private val userRepository: UserRepository by inject()

    fun login(email: String, password: String, FCMToken: String): LiveData<ApiResponse> {
        return userRepository.login(email, password, FCMToken)
    }

}
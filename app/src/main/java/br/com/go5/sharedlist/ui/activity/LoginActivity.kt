package br.com.go5.sharedlist.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.ApiResponse
import br.com.go5.sharedlist.data.model.User
import br.com.go5.sharedlist.data.viewmodel.LoginViewModel
import br.com.go5.sharedlist.data.viewmodel.ViewModelFactory
import br.com.go5.sharedlist.persistence.UserInfo
import org.jetbrains.anko.intentFor

import kotlinx.android.synthetic.main.activity_login.*
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.viewModel = createViewModel()
        spinner.hide()
        checkUserIsLoggedIn()
        btnLogin.setOnClickListener {
            validateLogin()
        }
    }

    private fun createViewModel(): LoginViewModel {
        val factory = ViewModelFactory()
        return ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)
    }

    private fun checkUserIsLoggedIn() {
        if (UserInfo.isLogged) {
            startActivity(intentFor<MainActivity>())
            this.finish()
        }
    }

    private fun validateLogin() {
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()
        val fcmToken = UserInfo.fcmToken
        this.signIn(username, password, fcmToken)
    }

    private fun signIn(email: String, password: String, FCMToken: String) {
        spinner.show()
        viewModel.login(email, password, FCMToken).observe(this, Observer<ApiResponse> {
                spinner.hide()
                if (it != null) {
                    if (it.result != null) {
                        redirectToMainActivity(it.result as User)
                    } else {
                        showAlertDialog(it.errorMessage!!)
                    }
                }  else {
                    showAlertDialog("Erro ao conectar com o servidor")
                }
            })
    }

    private fun showAlertDialog(message: String) {
        MaterialDialog(this@LoginActivity).show {
            title(R.string.alert)
            message(text = message)
            positiveButton(R.string.agree)
        }
    }

    private fun redirectToMainActivity(user: User) {
        UserInfo.isLogged = true
        UserInfo.username = user.email
        UserInfo.id = user.id
        startActivity(intentFor<MainActivity>())
        this.finish()
    }

}

package br.com.go5.sharedlist.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.User
import br.com.go5.sharedlist.network.RetrofitInit
import br.com.go5.sharedlist.persistence.UserInfo
import org.jetbrains.anko.intentFor

import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread
import retrofit2.HttpException


class LoginActivity : AppCompatActivity() {

    private val retrofit: RetrofitInit by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        spinner.hide()
        checkUserIsLoggedIn()

        btnLogin.setOnClickListener {
            validateLogin()
        }
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
//        val username = "lucas.saldanha@sga.pucminas.br"
//        val password = "123"

        spinner.show()
        GlobalScope.launch {
            try {
                val response = retrofit.userService().login(username, password, fcmToken).await()
                signIn(response)
            } catch (exception: HttpException) {
//                val jsonError = JSONObject(exception.response().errorBody()?.string())
//                showAlertDialog(jsonError.getString("message"))
                runOnUiThread {
                    showAlertDialog("Usuário/Senha incorreto(s)")
                }
            } catch (exception: Exception) {
                runOnUiThread {
                    showAlertDialog(getString(R.string.server_error))
                }
            } finally {
                runOnUiThread {
                    spinner.hide()
                }
            }
        }
    }

    private fun showAlertDialog(message: String) {
        MaterialDialog(this@LoginActivity).show {
            title(R.string.alert)
            message(text = message)
            positiveButton(R.string.agree)
        }
    }

    private fun signIn(user: User) {
        UserInfo.isLogged = true
        UserInfo.username = user.email
        UserInfo.id = user.id
        startActivity(intentFor<MainActivity>())
        this.finish()
    }

}

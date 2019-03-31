package br.com.go5.sharedlist.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.go5.sharedlist.R
import br.com.go5.sharedlist.data.model.User
import br.com.go5.sharedlist.network.RetrofitInit
import org.jetbrains.anko.intentFor

import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject
import com.afollestad.materialdialogs.MaterialDialog


class LoginActivity : AppCompatActivity() {

    private val retrofit: RetrofitInit by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        spinner.hide()

        btnLogin.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        validateLogin()
//        startActivity(intentFor<MainActivity>())
    }

    private fun validateLogin() {
//        val username = editTextUsername.text.toString()
//        val password = editTextPassword.text.toString()
        val username = "lucas.saldanha@sga.pucminas.br"
        val password = "123"

        spinner.show()
        retrofit.userService().login(username, password).enqueue(
            object: Callback<User?> {

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    spinner.hide()
                    MaterialDialog(this@LoginActivity).show {
                        title(R.string.alert)
                        message(R.string.server_error)
                        positiveButton(R.string.agree)
                    }
                }

                override fun onResponse(call: Call<User?>, response: Response<User?>) {
                    spinner.hide()
                    if (response.isSuccessful) {
                        startActivity(intentFor<MainActivity>())
                    } else {
                        try {
                            val jsonError = JSONObject(response.errorBody()?.string())
                            MaterialDialog(this@LoginActivity).show {
                                title(R.string.alert)
                                message(text = jsonError.getString("message"))
                                positiveButton(R.string.agree)
                            }
                        } catch (e: Exception) {
                            MaterialDialog(this@LoginActivity).show {
                                title(R.string.alert)
                                message(text = e.message)
                                positiveButton(R.string.agree)
                            }
                        }
                    }
                }
            }
        )
    }
}

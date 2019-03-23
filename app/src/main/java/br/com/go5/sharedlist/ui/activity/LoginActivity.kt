package br.com.go5.sharedlist.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.go5.sharedlist.R
import org.jetbrains.anko.intentFor

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        startActivity(intentFor<MainActivity>())
    }
}

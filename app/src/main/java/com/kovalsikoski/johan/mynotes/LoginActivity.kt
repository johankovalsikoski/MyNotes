package com.kovalsikoski.johan.mynotes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        realm = Realm.getDefaultInstance()

        tv_recover.setOnClickListener {
            if(isValidEmail()){
                if(isValidEmailPattern(et_user.text.toString())){

                } else {
                    Toast.makeText(this, "Formato de e-mail inválido", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Informe o e-mail para receber a dica de sua senha", Toast.LENGTH_LONG).show()
            }
        }

        btn_login.setOnClickListener {
            login()
        }

    }

    private fun login() {
        if (checkAllFields()) {
            if (isValidEmailPattern(et_user.text.toString())) {
                realm.beginTransaction()

                val result = realm.where(UserModel::class.java)
                        .equalTo("email", et_user.text.toString())
                        .and()
                        .equalTo("password", et_password.text.toString())
                        .findFirst()

                if (result != null) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Formato de e-mail inválido", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkAllFields(): Boolean {
        return if(isValidEmail()){
            if(isValidPassword()){
                true
            } else {
                Toast.makeText(this, "Campo de senha vazio", Toast.LENGTH_LONG).show()
                false
            }
        } else {
            Toast.makeText(this, "Campo de e-mail vazio", Toast.LENGTH_LONG).show()
            return false
        }
    }

    private fun isValidEmail(): Boolean = et_user.text.toString().isNotBlank()
    private fun isValidPassword(): Boolean = et_password.text.toString().isNotBlank()

    private fun isValidEmailPattern(email: String): Boolean =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)\$",
                    Pattern.CASE_INSENSITIVE).matcher(email).matches()

}
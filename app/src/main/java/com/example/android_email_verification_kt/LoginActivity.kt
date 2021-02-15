package com.example.android_email_verification_kt

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

import com.parse.ParseException
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    private var navigatesignup: Button? = null
    private var login: Button? = null
    private var password: TextInputEditText? = null
    private var username: TextInputEditText? = null
    private var progressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressDialog = ProgressDialog(this@LoginActivity)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        navigatesignup = findViewById(R.id.navigatesignup)

        login?.setOnClickListener {
            login(
                username?.text.toString(),
                password?.text.toString()
            )
        }

        navigatesignup?.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    SignUpActivity::class.java
                )
            )
        }

    }

    private fun login(username: String, password: String) {
        progressDialog?.show()
        ParseUser.logInInBackground(
            username,
            password
        ) { parseUser: ParseUser?, e: ParseException? ->
            progressDialog?.dismiss()
            if (parseUser != null) {
                showAlert("Login Successful", "Welcome, $username!", false)
            } else {
                ParseUser.logOut()
                showAlert("Login Fail", e?.message + " Please try again", true)
            }
        }
    }


    private fun showAlert(title: String, message: String, error: Boolean) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
                // don't forget to change the line below with the names of your Activities
                if (!error) {
                    val intent = Intent(this, LogoutActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }

            }
        val ok = builder.create()
        ok.show()
    }
}
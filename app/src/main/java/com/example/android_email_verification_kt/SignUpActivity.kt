package com.example.android_email_verification_kt

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseUser
import com.parse.SignUpCallback

class SignUpActivity : AppCompatActivity() {
    private var back: ImageView? = null
    private var signUp: Button? = null
    private var username: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private var passwordagain: TextInputEditText? = null
    private var email: TextInputEditText? = null
    private var progressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        back = findViewById(R.id.back)
        signUp = findViewById(R.id.signup)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        passwordagain = findViewById(R.id.passwordagain)
        email = findViewById(R.id.email)


        signUp?.setOnClickListener {
            if (password?.text.toString() == passwordagain?.text.toString() && !TextUtils.isEmpty(
                    username?.text.toString()
                )
            )
                signUp(username?.text.toString(), password?.text.toString(), email?.text.toString())
            else
                Toast.makeText(
                    this,
                    "Make sure that the values you entered are correct.",
                    Toast.LENGTH_SHORT
                ).show()
        }



        back?.setOnClickListener {
            finish()
        }
    }

    private fun signUp(username: String, password: String, email: String) {
        progressDialog?.show()
        val user = ParseUser()
        user.username = username
        user.setPassword(password)
        user.email = email
        user.signUpInBackground(SignUpCallback {
            progressDialog?.dismiss()
            if (it == null) {
                ParseUser.logOut();
                showAlert("Account Created Successfully!","Please verify your email before Login", false)
            } else {
                ParseUser.logOut();
                showAlert("Error Account Creation failed","Account could not be created" + " :" + it.message,true)
            }
        })
    }


    private fun showAlert(title: String, message: String, error: Boolean) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
                // don't forget to change the line below with the names of your Activities
                if (!error) {
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        val ok = builder.create()
        ok.show()
    }


}
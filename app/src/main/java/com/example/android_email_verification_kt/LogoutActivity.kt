package com.example.android_email_verification_kt

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.parse.ParseException
import com.parse.ParseUser

class LogoutActivity : AppCompatActivity() {

    var logout: Button? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)

        logout = findViewById(R.id.logout)
        progressDialog = ProgressDialog(this)

        logout?.setOnClickListener {
            progressDialog!!.show()
            ParseUser.logOutInBackground { e: ParseException? ->
                progressDialog!!.dismiss()
                if (e == null)
                    showAlert("So, you're going...", "Ok...Bye-bye then")
            }
        }
    }

    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
                // don't forget to change the line below with the names of your Activities
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        val ok = builder.create()
        ok.show()
    }
}
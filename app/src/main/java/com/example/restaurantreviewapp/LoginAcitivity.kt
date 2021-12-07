package com.example.restaurantreviewapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginAcitivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()

    lateinit var backbtn: Button
    lateinit var loginbtn: Button
    lateinit var emailText: EditText
    lateinit var passwordText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_acitivity)

        emailText = findViewById<EditText>(R.id.input_email)
        passwordText = findViewById<EditText>(R.id.input_password)

        backbtn = findViewById(R.id.backbtn)
        loginbtn = findViewById((R.id.loginbtn))

        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                }
            }

        backbtn.setOnClickListener{
             val newIntent = Intent(this, MainActivity::class.java)
             startForResult.launch(newIntent)
        }

        loginbtn.setOnClickListener { view ->
            logInUser(view)
        }
    }

    private fun logInUser(view: View){

        if(emailText.text.toString().isEmpty()){
            emailText.error = "Please insert your email"
            emailText.requestFocus()
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString()).matches()) {
            emailText.error = "Please insert a valid email"
            emailText.requestFocus()
        }

        if(passwordText.text.toString().isEmpty()){
            passwordText.error = "Please insert your password"
            passwordText.requestFocus()
        }

        mAuth.signInWithEmailAndPassword(
            emailText.text.toString(),
            passwordText.text.toString()
        )
            .addOnCompleteListener(
                this
            ){ task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val newIntent = Intent(this, MenuActivity::class.java)
                    newIntent.putExtra("id", user?.email)
                    startActivity(newIntent)
                } else {
                    showMessage(view, getString(R.string.failedsignin))
                }
            }
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}
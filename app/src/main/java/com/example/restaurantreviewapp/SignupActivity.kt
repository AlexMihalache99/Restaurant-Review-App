package com.example.restaurantreviewapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    lateinit var signupbtn: Button
    lateinit var backbtn:Button
    lateinit var emailText: EditText
    lateinit var passwordText: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        backbtn = findViewById(R.id.backbtn)
        signupbtn = findViewById(R.id.signupbtn)
        emailText = findViewById(R.id.input_email)
        passwordText = findViewById(R.id.input_password)
        auth = FirebaseAuth.getInstance()

        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                }
            }
        backbtn.setOnClickListener {
            val newIntent = Intent(this, MainActivity::class.java)
            startForResult.launch(newIntent)
        }

        signupbtn.setOnClickListener{view ->
            signUpUser(view)
        }
    }

    private fun signUpUser(view: View) {

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

        auth.createUserWithEmailAndPassword(emailText.text.toString().trim(), passwordText.text.toString().trim())
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    val user = auth.currentUser
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                } else{
                    showMessage(view, getString(R.string.signupfailed))
                }
            }
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}


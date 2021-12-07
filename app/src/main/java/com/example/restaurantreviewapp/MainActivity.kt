package com.example.restaurantreviewapp

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.util.ExtraConstants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.util.Log
import com.firebase.ui.auth.AuthUI.TAG
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient

import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.*


class MainActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private var RC_SIGN_IN : Int = 123
    private lateinit var auth: FirebaseAuth

    lateinit var skipbtn: Button
    lateinit var loginbtn: Button
    lateinit var signupbtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        skipbtn = findViewById(R.id.skipbtn)
        loginbtn = findViewById(R.id.loginbtn)
        signupbtn = findViewById(R.id.signupbtn)


        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                }
            }

        skipbtn.setOnClickListener{
            val newIntent = Intent(this, MenuActivity::class.java)
            startForResult.launch(newIntent)
        }

        loginbtn.setOnClickListener{
            val newIntent = Intent(this, LoginAcitivity::class.java)
            startForResult.launch(newIntent)
        }

        signupbtn.setOnClickListener{
            val newIntent = Intent(this, SignupActivity::class.java)
            startForResult.launch(newIntent)
        }

        auth = FirebaseAuth.getInstance()

        createRequest()

        findViewById<View>(R.id.googlebtn).setOnClickListener(View.OnClickListener() {
            fun onClick(view: View?) {
                signIn()
            }
        })

    }

    private fun createRequest() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                }
            }
        startForResult.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this, OnCompleteListener<AuthResult> {
                fun onComplete(task: Task<*>) {
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.getCurrentUser()
                        val intent = Intent(applicationContext, MenuActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "Sorry auth failed.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }


}
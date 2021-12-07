package com.example.restaurantreviewapp.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.restaurantreviewapp.MainActivity
import com.example.restaurantreviewapp.R
import com.example.restaurantreviewapp.WriteReviewActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.Intent as Intent


class ProfileFragment : Fragment() {
    private lateinit var signoutbtn: Button
    private lateinit var profileName: EditText
    private lateinit var mFireBaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var reviewBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFireBaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        signoutbtn = view.findViewById(R.id.signoutbtn)
        profileName = view.findViewById(R.id.profilename)
        reviewBtn = view.findViewById(R.id.review)
        user = FirebaseAuth.getInstance().currentUser!!
        profileName.setText(user.email)

        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                }
            }

        reviewBtn.setOnClickListener {
            val intent = Intent(activity,WriteReviewActivity::class.java)
            startForResult.launch(intent)
        }

        signoutbtn.setOnClickListener {
            mFireBaseAuth.signOut()
            profileName.setText("")
            val intent = Intent(activity,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
            startForResult.launch(intent)
        }
        return  view
    }
}
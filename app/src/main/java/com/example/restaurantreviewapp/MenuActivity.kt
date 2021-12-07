package com.example.restaurantreviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.restaurantreviewapp.fragments.ListFragment
import com.example.restaurantreviewapp.fragments.ProfileFragment
import com.example.restaurantreviewapp.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {

    private val searchFragment = SearchFragment()
    private val profileFragment = ProfileFragment()
    private val listFragment = ListFragment()
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mFireBaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        mFireBaseAuth = FirebaseAuth.getInstance()
        replaceFragment(searchFragment)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.search -> replaceFragment(searchFragment)
                R.id.me -> replaceFragment(profileFragment)
                R.id.list -> replaceFragment(listFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}
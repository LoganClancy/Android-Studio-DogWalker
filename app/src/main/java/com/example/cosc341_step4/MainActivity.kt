package com.example.cosc341_step4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    private lateinit var btnHome: Button
    private lateinit var btnEvents: Button
    private lateinit var btnPeople: Button
    private lateinit var btnMap: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnHome = findViewById(R.id.button)
        btnEvents = findViewById(R.id.button2)
        btnPeople = findViewById(R.id.button3)
        btnMap = findViewById(R.id.mapButton)

        // Load your Event/Walk fragment as home screen
        if (savedInstanceState == null) {
            loadFragment(EventWalkProfileFragment())
        }

        // Bottom navigation buttons
        btnHome.setOnClickListener {
            loadFragment(EventWalkProfileFragment())
        }

        btnEvents.setOnClickListener {
            loadFragment(JoinEventsFragment())
        }

        btnPeople.setOnClickListener {
            loadFragment(PeopleJoiningFragment())
        }

        btnMap.setOnClickListener {
            onClickMap(it)
        }
    }

    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun onClickMap(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}
package com.example.cosc341_step4

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.cosc341_step4.chat.ChatListActivity
import com.example.cosc341_step4.notifications.NotificationCenter


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load your Pet Meetup page when app starts
        if (savedInstanceState == null) {
            loadFragment(EventWalkProfileFragment())
        }
    }

    fun onClickMap(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    fun onClickChat(view: View) {
        val intent = Intent(this, ChatListActivity::class.java)
        startActivity(intent)
    }

    fun onClickEvent(view: View) {
        // FIXED: Now opens Events page instead of Chat
        loadFragment(JoinEventsFragment())
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }



    fun onClickNotif(view: View) {
        val intent = Intent(this, NotificationCenter::class.java)
        startActivity(intent)
    }
}
package com.example.kitapal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register_activity)

        val loginPage: TextView = findViewById(R.id.login)
        loginPage.setOnClickListener{
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}
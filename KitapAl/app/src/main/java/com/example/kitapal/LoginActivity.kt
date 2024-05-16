package com.example.kitapal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    public fun registerPage(view: View) {
        val intent: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_activity)

        val registerPage: TextView = findViewById<TextView>(R.id.signupText)
        registerPage.setOnClickListener{
            val intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }


}
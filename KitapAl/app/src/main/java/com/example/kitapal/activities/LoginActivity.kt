package com.example.kitapal.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.kitapal.R
import com.example.kitapal.models.User

class LoginActivity : AppCompatActivity() {

    public fun registerPage(view: View) {
        val intent: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    val userDAO = MainActivity.database.getUserDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_activity)

        val registerPage: TextView = findViewById<TextView>(R.id.signupText)
        registerPage.setOnClickListener{
            val intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val logButton = findViewById<Button>(R.id.loginButton)
        logButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            val usersList = userDAO.getAllUsers()
            val filteredList = usersList.filter { it.username == username && it.password == password}
            if(!filteredList.isEmpty()){
                MainActivity.loggedIn = true
                MainActivity.user = User(id = filteredList.elementAt(0).id, username = filteredList.elementAt(0).username, email = filteredList.elementAt(0).email, password = filteredList.elementAt(0).password)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                findViewById<EditText>(R.id.username).text = null
                findViewById<EditText>(R.id.password).text = null
                val toast = Toast.makeText(applicationContext, "Wrong Username or Password! Please try again!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, -10)
                toast.show()
            }

        }

    }


}
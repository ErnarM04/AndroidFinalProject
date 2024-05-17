package com.example.kitapal

import FirstFragment
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kitapal.models.User

class RegisterActivity : AppCompatActivity() {

    val userDAO = MainActivity.database.getUserDAO()
    val cartDAO = MainActivity.database.getCartDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register_activity)

        val loginPage: TextView = findViewById(R.id.login)
        loginPage.setOnClickListener{
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val regButton = findViewById<Button>(R.id.regButton)
        regButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.username).text.toString()
            val email = findViewById<EditText>(R.id.email).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            val usersList = userDAO.getAllUsers()
            val filteredList = usersList.filter { it.username == username || it.email == email}
            if(filteredList.isEmpty()){
                MainActivity.loggedIn = true
                val id = userDAO.findUserByName(username).elementAt(0).id

                MainActivity.user = User(id = id, username = username, email = email, password = password)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                findViewById<EditText>(R.id.username).text = null
                findViewById<EditText>(R.id.password).text = null
                val toast = Toast.makeText(applicationContext, "Same E-mail or Username exists! Please try again!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, -10)
                toast.show()
            }
        }



    }

    private fun addUser(username: String, email: String, password: String){
        userDAO.addUser(User(username = username, email =  email, password =  password))
    }

}
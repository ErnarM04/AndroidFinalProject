package com.example.kitapal.activities

import com.example.kitapal.fragments.FirstFragment
import com.example.kitapal.fragments.SecondFragment
import com.example.kitapal.fragments.ThirdFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kitapal.R
import com.example.kitapal.database.KitapDB
import com.example.kitapal.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var void: Any

    companion object{
        lateinit var database: KitapDB
        lateinit var user: User
        public var loggedIn: Boolean = false
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(
            applicationContext,
            KitapDB::class.java,
            KitapDB.NAME
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration().build()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val users = database.getUserDAO().getAllUsers()
                withContext(Dispatchers.Main) {
                    println("1")
                    println(users)
                    println("1")
                }
            }
        }

        val firstFragment= FirstFragment()
        val secondFragment= SecondFragment()
        val thirdFragment= ThirdFragment()
        val loginActivity= LoginActivity()

        setCurrentFragment(firstFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home ->setCurrentFragment(firstFragment)
                R.id.favorites ->setCurrentFragment(secondFragment)
                R.id.profile -> {
                    if (loggedIn) {
                        setCurrentFragment(thirdFragment)
                    } else {
                        val intent: Intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
            true
        }
    }


    public fun setUser(username: String, email: String, password: String){
        user = User(username = username, email = email, password = password)
    }

    fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,fragment)
            commit()
        }
}
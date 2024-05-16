package com.example.kitapal

import FirstFragment
import SecondFragment
import ThirdFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.kitapal.adapters.HomeAdapter
import com.example.kitapal.databinding.LoginActivityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    public var loggedIn: Boolean = false
    private lateinit var void: Any

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val firstFragment=FirstFragment()
        val secondFragment=SecondFragment()
        val thirdFragment=ThirdFragment()
        val loginActivity=LoginActivity()

        setCurrentFragment(firstFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(firstFragment)
                R.id.favorites->setCurrentFragment(secondFragment)
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




    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView,fragment)
            commit()
        }
}
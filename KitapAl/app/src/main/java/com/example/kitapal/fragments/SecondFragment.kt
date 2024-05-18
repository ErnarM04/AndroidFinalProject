package com.example.kitapal.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitapal.activities.MainActivity
import com.example.kitapal.R
import com.example.kitapal.activities.BookDetailActivity
import com.example.kitapal.adapters.BookmarkAdapter
import com.example.kitapal.adapters.HomeAdapter
import com.example.kitapal.api.BookApi
import com.example.kitapal.databinding.FragmentSecondBinding
import com.example.kitapal.databinding.FragmentThirdBinding
import com.example.kitapal.models.Book
import com.example.kitapal.models.User
import kotlinx.coroutines.sync.Mutex
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondFragment:Fragment(R.layout.fragment_second) {

    private var _binding : FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy {
        BookmarkAdapter(handleClick = {handleClick(it)})
    }
    val userDAO = MainActivity.database.getUserDAO()
    val favoriteDAO = MainActivity.database.getFavoriteDAO()
    var list: ArrayList<Book> = arrayListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        val view: View = inflater.inflate(R.layout.fragment_second, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ids = favoriteDAO.findCartById(MainActivity.user.id)
        for (i in 0..ids.length/12-1){
            val id = ids.slice(i*12..i*12+11)
            val client = BookApi.instance
            val response = client.getBookById(id)
            response.enqueue(object : Callback<Book>{
                override fun onResponse(call: Call<Book>, response: Response<Book>) {
                    response.body()?.let { list.add(it) }
                }

                override fun onFailure(call: Call<Book>, t: Throwable) {
                    println("HttpResponse: ${t.message}")
                }

            })
        }

        adapter.setBooks(list)


        setupUI()

    }

    private fun setupUI(){
        with(binding){
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleClick(id: String) {
        val intent = Intent(context, BookDetailActivity::class.java)
        intent.putExtra("book", id)
        startActivity(intent)
    }
}

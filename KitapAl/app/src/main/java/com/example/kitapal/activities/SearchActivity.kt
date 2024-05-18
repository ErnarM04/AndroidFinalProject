package com.example.kitapal.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitapal.R
import com.example.kitapal.adapters.SearchAdapter
import com.example.kitapal.api.BookApi
import com.example.kitapal.databinding.SearchViewBinding
import com.example.kitapal.models.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private var _binding: SearchViewBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { SearchAdapter(handleClick = {handleClick(it)}) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_view)

        val client = BookApi.instance

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    val response = client.getBooks("$it")
                    response.enqueue(object : Callback<ApiResponse> {
                        override fun onResponse(
                            call: Call<ApiResponse>,
                            response: Response<ApiResponse>
                        ) {
                            var list = response.body()?.books
                            list = list?.filter { it.volumeInfo.title != null }
                            if (list != null){
                                adapter?.setBooks(booksList = list.map { it })
                            }
                            println(adapter.itemCount)
                            println(it)
                        }


                        override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                            println("HttpResponse: ${t.message}")
                        }

                    })
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    println(it)
                }
                return true
            }

        })

    }

    fun setupUI(){
        with(binding){
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
        }
    }

    private fun handleClick(id: String) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra("book", id)
        startActivity(intent)
    }

}
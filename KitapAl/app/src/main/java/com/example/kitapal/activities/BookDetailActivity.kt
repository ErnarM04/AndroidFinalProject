package com.example.kitapal.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kitapal.api.BookApi
import com.example.kitapal.database.FavoriteDAO
import com.example.kitapal.databinding.BookDetailBinding
import com.example.kitapal.models.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailActivity: AppCompatActivity() {

    private lateinit var binding: BookDetailBinding
    val favoriteDAO: FavoriteDAO = MainActivity.database.getFavoriteDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Use binding.root instead of layout resource ID
        val bookId: String = intent.extras?.getSerializable("book").toString()
        println(bookId)

        val client = BookApi.instance
        val response = client.getBookById(bookId)
        response.enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                if (response.isSuccessful) {
                    val book = response.body()
                    if (book != null) {
                        with(binding) {
                            val coverLink = book.volumeInfo.imageLinks?.thumbnail ?: ""
                            if (coverLink.isNotEmpty()) {
                                Glide.with(applicationContext)
                                    .load(coverLink.replaceFirst("http", "https"))  // Use replaceFirst to add 's' for HTTPS
                                    .into(cover)
                            }

                            title.text = book.volumeInfo.title
                            authors.text = book.volumeInfo.authors?.joinToString(", ") ?: ""
                            categories.text = book.volumeInfo.categories?.joinToString(", ") ?: ""
                            description.text = book.volumeInfo.description
                            back.setOnClickListener{
                                onBackPressed()
                            }
                            bookmark.setOnClickListener{
                                var ids: String = favoriteDAO.findCartById(MainActivity.user.id)
                                if (bookId in ids == false){
                                    ids = ids + bookId.toString()
                                }
                                favoriteDAO.updateCart(MainActivity.user.id, ids)
                            }

                        }
                    } else {
                        Toast.makeText(this@BookDetailActivity, "Book not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@BookDetailActivity, "Failed to retrieve book details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                println("HttpResponse: ${t.message}")
                Toast.makeText(this@BookDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

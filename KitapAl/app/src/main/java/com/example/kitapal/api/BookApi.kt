package com.example.kitapal.api

import com.example.kitapal.models.ApiResponse
import com.example.kitapal.models.Book
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object BookApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val instance = retrofit.create(ApiService::class.java)

    interface ApiService {
        @GET("volumes/")
        fun getBooks(@Query("q") query: String): Call<ApiResponse>

        @GET("volumes/{id}")
        fun getBookById(@Path("id") id: String): Call<Book>
    }
}
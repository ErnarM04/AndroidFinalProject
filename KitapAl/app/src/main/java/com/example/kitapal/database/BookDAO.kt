package com.example.kitapal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kitapal.models.BookDB

@Dao
interface BookDAO {

    @Query("SELECT * FROM BOOKDB")
    fun getAllBooks() : LiveData<List<BookDB>>

    @Insert
    fun addBook(bookDB: BookDB)

    @Query("DELETE FROM BOOKDB WHERE id = :id")
    fun removeBook(id: String)

}
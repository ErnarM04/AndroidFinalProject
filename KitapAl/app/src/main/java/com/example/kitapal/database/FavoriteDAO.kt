package com.example.kitapal.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kitapal.models.Favorite

@Dao
interface FavoriteDAO {

    @Query("SELECT * FROM Favorite")
    fun getAllCarts() : List<Favorite>

    @Insert
    fun addCart(favorite: Favorite)

    @Query("SELECT BookIDs FROM Favorite WHERE userID = :id")
    fun findCartById(id: Int) : String

    @Query("UPDATE Favorite SET bookIDs = :books WHERE userID = :id")
    fun updateCart(id: Int, books: String)

    @Query("DELETE FROM Favorite WHERE userID = :id")
    fun removeCart(id: Int)

}
package com.example.kitapal.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kitapal.models.Cart

@Dao
interface CartDAO {

    @Query("SELECT * FROM CART")
    fun getAllCarts() : List<Cart>

    @Insert
    fun addCart(cart: Cart)

    @Query("SELECT * FROM CART WHERE userID = :id")
    fun findCartById(id: Int) : List<Cart>

    @Query("UPDATE CART SET bookIDs = :books WHERE userID = :id")
    fun updateCart(id: Int, books: List<String>)

    @Query("DELETE FROM CART WHERE userID = :id")
    fun removeCart(id: Int)

}
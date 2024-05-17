package com.example.kitapal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kitapal.models.User
import com.example.kitapal.models.Cart

@Database(entities = [User::class, Cart::class], version = 1)
abstract class KitapDB : RoomDatabase() {

    companion object{
        const val NAME = "Kital_Al"
    }

    abstract fun getUserDAO() : UserDAO

    abstract fun getCartDAO() : CartDAO


}
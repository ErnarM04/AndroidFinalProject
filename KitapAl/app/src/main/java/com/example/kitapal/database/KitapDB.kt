package com.example.kitapal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kitapal.models.User
import com.example.kitapal.models.Favorite

@Database(entities = [User::class, Favorite::class], version = 3)
abstract class KitapDB : RoomDatabase() {

    companion object{
        const val NAME = "Kital_Al"
    }

    abstract fun getUserDAO() : UserDAO

    abstract fun getFavoriteDAO() : FavoriteDAO


}
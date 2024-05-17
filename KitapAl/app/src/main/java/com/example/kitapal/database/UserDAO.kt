package com.example.kitapal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.kitapal.models.User
import retrofit2.http.Query

@Dao
interface UserDAO {

    @androidx.room.Query("SELECT * FROM USER")
    fun getAllUsers() : List<User>

    @Insert
    fun addUser(user: User)

    @androidx.room.Query("SELECT * FROM USER WHERE username = :username")
    fun findUserByName(username: String) : List<User>

    @androidx.room.Query("UPDATE USER SET username = :username, email = :email, password = :password WHERE id = :id")
    fun updateUser(id: Int, username: String, email: String, password: String)

    @androidx.room.Query("DELETE FROM USER WHERE id = :id")
    fun removeUser(id: Int)

}
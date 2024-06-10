package com.example.trivia_game.room.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg user: User) : LongArray

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User ORDER BY points DESC LIMIT 10")
    suspend fun getTopUsers() : List<User>

    @Query("SELECT * FROM User WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM User WHERE uid = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM User")
    suspend fun getAllUsers() : List<User>
}
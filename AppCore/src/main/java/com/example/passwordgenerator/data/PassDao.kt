package com.example.passwordgenerator.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PassDao {
    @Query("SELECT * FROM passwords ORDER BY id")
    fun getPasswords(): Flow<List<PassEntity>>

    @Query("SELECT * FROM passwords WHERE id = :passId")
    fun getPass(passId: Long): Flow<PassEntity>

    @Query("UPDATE passwords SET title=:title WHERE id = :passId")
    suspend fun editTitle(title: String, passId: Long)

    @Query("UPDATE passwords SET login=:login WHERE id = :passId")
    suspend fun editLogin(login: String, passId: Long)

    @Query("UPDATE passwords SET pass=:pass WHERE id = :passId")
    suspend fun editPass(pass: String, passId: Long)

    @Query("DELETE FROM passwords WHERE id = :passId")
    suspend fun deletePass(passId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pass: PassEntity)
}
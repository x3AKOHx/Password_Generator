package com.example.passwordgenerator.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PassEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val passId: Long,
    val title: String,
    val login: String,
    val pass: String,
)
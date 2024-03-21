package com.example.marvelcharacters.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val password: String,
    val username: String,
    val email: String,
)

@Entity(tableName = "favorite_characters")
data class FavoriteCharacter(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val comics: String,
    val series: String,
    val stories: String,
    val events: String,
    val imageUrl: String
)
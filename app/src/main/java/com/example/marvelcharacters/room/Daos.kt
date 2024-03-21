package com.example.marvelcharacters.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): User?
}

@Dao
interface FavoriteCharacterDao {
    @Insert
    fun insert(character: FavoriteCharacter)

    @Delete
    fun delete(character: FavoriteCharacter)

    @Query("SELECT * FROM favorite_characters WHERE name = :name")
    fun getFavoriteCharacterByName(name: String): FavoriteCharacter?

    @Query("SELECT * FROM favorite_characters")
    fun getAllFavoriteCharacters(): List<FavoriteCharacter>

}

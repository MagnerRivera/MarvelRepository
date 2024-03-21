package com.example.marvelcharacters.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, FavoriteCharacter::class], version = 3)
abstract class MarvelCharactersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}
package com.example.marvelcharacters.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class MarvelCharactersDatabase : RoomDatabase()  {

    abstract fun userDao(): UserDao

}
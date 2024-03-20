package com.example.marvelcharacters.di

import android.app.Application
import androidx.room.Room
import com.example.marvelcharacters.room.MarvelCharactersDatabase
import com.example.marvelcharacters.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Proporciono la instancia única de la base de datos MarvelCharacters-database
    @Provides
    @Singleton
    fun provideMarvelCharactersDatabase(application: Application): MarvelCharactersDatabase {
        return Room.databaseBuilder(
            application,
            MarvelCharactersDatabase::class.java,
            "marvelcharacters-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: MarvelCharactersDatabase): UserDao {
        return appDatabase.userDao()
    }
}
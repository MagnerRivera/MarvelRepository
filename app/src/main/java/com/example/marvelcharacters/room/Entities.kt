package com.example.marvelcharacters.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.marvelcharacters.retrofit.MarvelResourceItem
import com.example.marvelcharacters.utils.Converters
import java.util.UUID
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val password: String,
    val username: String,
    val email: String,
)

@Entity(tableName = "favorite_characters")
@TypeConverters(Converters::class)
@Parcelize
data class FavoriteCharacter(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val thumbnailPath: String,
    val thumbnailExtension: String,
    val comicsAvailable: Int,
    val comicsCollectionURI: String,
    val comicsItems: List<MarvelResourceItem>,
    val comicsReturned: Int,
    val seriesAvailable: Int,
    val seriesCollectionURI: String,
    val seriesItems: List<MarvelResourceItem>,
    val seriesReturned: Int,
    val storiesAvailable: Int,
    val storiesCollectionURI: String,
    val storiesItems: List<MarvelResourceItem>,
    val storiesReturned: Int,
    val eventsAvailable: Int,
    val eventsCollectionURI: String,
    val eventsItems: List<MarvelResourceItem>,
    val eventsReturned: Int
) : Parcelable
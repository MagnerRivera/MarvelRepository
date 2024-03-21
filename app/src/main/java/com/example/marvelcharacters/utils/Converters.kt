package com.example.marvelcharacters.utils

import androidx.room.TypeConverter
import com.example.marvelcharacters.retrofit.MarvelResourceItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromJson(json: String): List<MarvelResourceItem> {
        val listType = object : TypeToken<List<MarvelResourceItem>>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(list: List<MarvelResourceItem>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
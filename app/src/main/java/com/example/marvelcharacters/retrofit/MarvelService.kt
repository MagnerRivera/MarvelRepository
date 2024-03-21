package com.example.marvelcharacters.retrofit

import com.example.marvelcharacters.utils.Constans
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {
    @GET(Constans.GET)
    fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("ts") timeStamp: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<MarvelResponse>
}
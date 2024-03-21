package com.example.marvelcharacters.retrofit

import com.example.marvelcharacters.utils.Constans
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import java.util.Calendar

object MarvelApi {
    private const val BASE_URL = Constans.BASE_URL
    private const val PUBLIC_KEY = Constans.PUBLIC_KEY
    private const val PRIVATE_KEY = Constans.PRIVATE_KEY

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val marvelService = retrofit.create(MarvelService::class.java)

    fun getCharacters(offset: Int, limit: Int): List<MarvelCharacter> {
        val timeStamp = Calendar.getInstance().timeInMillis.toString()
        val hash = generateHash(timeStamp)

        val response = marvelService.getCharacters(
            PUBLIC_KEY,
            hash,
            timeStamp,
            offset,
            limit
        ).execute()

        return response.body()?.data?.results.orEmpty()
    }


    private fun generateHash(timeStamp: String): String {
        val input = "$timeStamp$PRIVATE_KEY$PUBLIC_KEY"
        val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
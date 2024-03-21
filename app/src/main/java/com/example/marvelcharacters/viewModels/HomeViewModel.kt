package com.example.marvelcharacters.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelcharacters.retrofit.MarvelApi
import com.example.marvelcharacters.retrofit.MarvelCharacter
import com.example.marvelcharacters.room.FavoriteCharacter
import com.example.marvelcharacters.room.MarvelCharactersDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val db: MarvelCharactersDatabase) : ViewModel() {

    private var offset = 0
    private val charactersPerPage = 50
    private var loadedCharactersCount = 0
    var isFiltering: Boolean = false

    private val _characters = MutableLiveData<List<MarvelCharacter>>()
    val characters: MutableLiveData<List<MarvelCharacter>> get() = _characters

    fun fetchAllCharacters() {
        if (!isFiltering) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val charactersList = MarvelApi.getCharacters(offset, charactersPerPage)
                    // Actualiza la lista de personajes en lugar de reemplazarla
                    val updatedList = _characters.value.orEmpty().toMutableList()
                    updatedList.addAll(charactersList)
                    _characters.postValue(updatedList)
                    offset += charactersPerPage
                    loadedCharactersCount += charactersList.size
                } catch (e: SocketTimeoutException) {
                    Log.e("HomeViewModel", "Timeout al conectar al servidor: ${e.message}")
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Error al conectar al servidor: ${e.message}")
                    e.printStackTrace()
                }
            }
        }
    }

    fun addToFavorites(
        name: String,
        description: String,
        comics: String,
        series: String,
        stories: String,
        events: String,
        imageUrl: String
    ): Boolean {
        val character = FavoriteCharacter(
            name = name,
            description = description,
            comics = comics,
            series = series,
            stories = stories,
            events = events,
            imageUrl = imageUrl
        )
        return try {
            viewModelScope.launch(Dispatchers.IO) {
                val existingCharacter = db.favoriteCharacterDao().getFavoriteCharacterByName(name)
                if (existingCharacter == null) {
                    db.favoriteCharacterDao().insert(character)
                } else {
                    Log.e("HomeViewModel", "El personaje '$name' ya está agregado a favoritos")
                }
            }
            true
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error al agregar el personaje a favoritos: ${e.message}")
            e.printStackTrace()
            false
        }
    }
}
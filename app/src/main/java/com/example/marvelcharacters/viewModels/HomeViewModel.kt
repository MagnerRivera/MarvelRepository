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

    fun addToFavorites(character: MarvelCharacter): Boolean {
        val favoriteCharacter = FavoriteCharacter(
            name = character.name,
            description = character.description,
            thumbnailPath = character.thumbnail.path,
            thumbnailExtension = character.thumbnail.extension,
            comicsAvailable = character.comics.available,
            comicsCollectionURI = character.comics.collectionURI,
            comicsItems = character.comics.items,
            comicsReturned = character.comics.returned,
            seriesAvailable = character.series.available,
            seriesCollectionURI = character.series.collectionURI,
            seriesItems = character.series.items,
            seriesReturned = character.series.returned,
            storiesAvailable = character.stories.available,
            storiesCollectionURI = character.stories.collectionURI,
            storiesItems = character.stories.items,
            storiesReturned = character.stories.returned,
            eventsAvailable = character.events.available,
            eventsCollectionURI = character.events.collectionURI,
            eventsItems = character.events.items,
            eventsReturned = character.events.returned
        )

        return try {
            viewModelScope.launch(Dispatchers.IO) {
                val existingCharacter = db.favoriteCharacterDao().getFavoriteCharacterByName(character.name)
                if (existingCharacter == null) {
                    db.favoriteCharacterDao().insert(favoriteCharacter)
                } else {
                    Log.e("HomeViewModel", "El personaje '${character.name}' ya está agregado a favoritos")
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
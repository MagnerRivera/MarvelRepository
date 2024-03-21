package com.example.marvelcharacters.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelcharacters.room.FavoriteCharacter
import com.example.marvelcharacters.room.MarvelCharactersDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteCharactersViewModel @Inject constructor(private val db: MarvelCharactersDatabase) : ViewModel() {

    fun getAllFavoriteCharacters(callback: (List<FavoriteCharacter>) -> Unit) {
        viewModelScope.launch {
            val characters = withContext(Dispatchers.IO) {
                db.favoriteCharacterDao().getAllFavoriteCharacters()
            }
            callback(characters)
        }
    }

    fun deleteFavoriteCharacterByMarvelName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteCharacter = db.favoriteCharacterDao().getFavoriteCharacterByName(name)
            favoriteCharacter?.let {
                db.favoriteCharacterDao().delete(it)
            }
        }
    }
}
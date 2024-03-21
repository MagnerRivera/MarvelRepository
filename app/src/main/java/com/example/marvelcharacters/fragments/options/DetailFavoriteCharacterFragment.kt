package com.example.marvelcharacters.fragments.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.marvelcharacters.databinding.FragmentDetailFavoriteCharacterBinding
import com.example.marvelcharacters.retrofit.MarvelResourceItem
import com.example.marvelcharacters.room.FavoriteCharacter
import com.example.marvelcharacters.viewModels.FavoriteCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFavoriteCharacterFragment : Fragment() {

    private lateinit var binding: FragmentDetailFavoriteCharacterBinding
    private val args: DetailFavoriteCharacterFragmentArgs by navArgs()
    private val viewModel: FavoriteCharactersViewModel by viewModels()
    private val textEmpty = "No hay información correspondiente"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailFavoriteCharacterBinding.inflate(inflater)

        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.favoriteClear.setOnClickListener {
            deleteFromFavorites()
        }

        val character = args.character
        displayCharacterDetails(character)

        return binding.root
    }

    private fun displayCharacterDetails(character: FavoriteCharacter) {
        binding.apply {
            name.text = character.name
            description.text = character.description

            Glide.with(requireContext())
                .load("${character.thumbnailPath}.${character.thumbnailExtension}")
                .into(image)

            if (character.comicsItems.isNotEmpty()) {
                commits.text = formatItems(character.comicsItems)
            } else {
                commits.text = textEmpty
            }

            if (character.seriesItems.isNotEmpty()) {
                series.text = formatItems(character.seriesItems)
            } else {
                series.text = textEmpty
            }

            if (character.storiesItems.isNotEmpty()) {
                story.text = formatItems(character.storiesItems)
            } else {
                story.text = textEmpty
            }

            if (character.eventsItems.isNotEmpty()) {
                events.text = formatItems(character.eventsItems)
            } else {
                events.text = textEmpty
            }
        }
    }

    private fun formatItems(items: List<MarvelResourceItem>): String {
        return items.joinToString("\n* ") { it.name }
    }

    private fun deleteFromFavorites() {
        val character = args.character
        viewModel.deleteFavoriteCharacterByMarvelName(character.name)
        showToast()
        findNavController().navigateUp()
    }

    private fun showToast() {
        Toast.makeText(requireContext(), "Personaje eliminado de favoritos", Toast.LENGTH_SHORT).show()
    }
}
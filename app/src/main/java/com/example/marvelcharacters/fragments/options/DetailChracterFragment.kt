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
import com.example.marvelcharacters.databinding.FragmentDetailCharacterBinding
import com.example.marvelcharacters.retrofit.MarvelCharacter
import com.example.marvelcharacters.retrofit.MarvelResourceItem
import com.example.marvelcharacters.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailChracterFragment : Fragment() {

    private lateinit var binding: FragmentDetailCharacterBinding
    private val args: DetailChracterFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by viewModels()
    private val textEmpty = "No hay información correspondiente"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailCharacterBinding.inflate(inflater)

        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.favoriteSeletedDetail.setOnClickListener {
            addToFavorites()
        }

        val character = args.character
        displayCharacterDetails(character)

        return binding.root
    }

    private fun displayCharacterDetails(character: MarvelCharacter) {
        binding.apply {
            name.text = character.name

            if (character.description.isNotEmpty()) {
                description.text = character.description
            } else {
                description.text = textEmpty
            }

            Glide.with(requireContext())
                .load("${character.thumbnail.path}.${character.thumbnail.extension}")
                .into(image)

            if (character.comics.items.isNotEmpty()) {
                commits.text = formatItems(character.comics.items)
            } else {
                commits.text = textEmpty
            }

            if (character.series.items.isNotEmpty()) {
                series.text = formatItems(character.series.items)
            } else {
                series.text = textEmpty
            }

            if (character.stories.items.isNotEmpty()) {
                story.text = formatItems(character.stories.items)
            } else {
                story.text = textEmpty
            }

            if (character.events.items.isNotEmpty()) {
                events.text = formatItems(character.events.items)
            } else {
                events.text = textEmpty
            }
        }
    }

    private fun formatItems(items: List<MarvelResourceItem>): String {
        return items.joinToString("\n* ") { it.name }
    }

    private fun addToFavorites() {
        val character = args.character
        val success = viewModel.addToFavorites(character)
        if (success) {
            showToast("Personaje agregado a favoritos")
            findNavController().navigateUp()
        } else {
            showToast("Error al agregar el personaje a favoritos")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
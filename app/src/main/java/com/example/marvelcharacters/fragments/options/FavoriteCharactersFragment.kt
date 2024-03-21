package com.example.marvelcharacters.fragments.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvelcharacters.adapter.FavoriteCharacterAdapter
import com.example.marvelcharacters.databinding.FragmentFavoriteCharactersBinding
import com.example.marvelcharacters.room.FavoriteCharacter
import com.example.marvelcharacters.viewModels.FavoriteCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteCharactersFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteCharactersBinding
    private val viewModel: FavoriteCharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteCharactersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteCharacterAdapter = FavoriteCharacterAdapter(emptyList()) { character ->
            navigateToDetailFragment(character)
        }

        binding.rvCharacterFavorite.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = favoriteCharacterAdapter
        }

        viewModel.getAllFavoriteCharacters { characters ->
            favoriteCharacterAdapter.characters = characters
            favoriteCharacterAdapter.notifyDataSetChanged()
        }
    }

    private fun navigateToDetailFragment(character: FavoriteCharacter) {
        val action =
            FavoriteCharactersFragmentDirections.actionFavoriteCharactersFragmentToDetailFavoriteCharacterFragment(
                character
            )
        findNavController().navigate(action)
    }
}
package com.example.marvelcharacters.fragments.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.marvelcharacters.databinding.FragmentFavoriteCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteCharactersFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteCharactersBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteCharactersBinding.inflate(inflater)

        return binding.root
    }
}
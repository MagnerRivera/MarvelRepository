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
import com.example.marvelcharacters.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailChracterFragment : Fragment() {

    private lateinit var binding: FragmentDetailCharacterBinding
    private val args: DetailChracterFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by viewModels()

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


        val name = args.name
        val description = args.description
        val commits = args.commits
        val series = args.series
        val story = args.story
        val events = args.events
        val thumbnail = args.thumbnail

        binding.name.text = name
        binding.description.text = description
        binding.rating.text = commits
        binding.ratingSeries.text = series
        binding.ratingStories.text = story
        binding.ratingEvents.text = events

        Glide.with(requireContext())
            .load(thumbnail)
            .into(binding.image)

        return binding.root
    }

    private fun addToFavorites() {
        val name = args.name
        val description = args.description
        val comics = args.commits
        val series = args.series
        val stories = args.story
        val events = args.events
        val imageUrl = args.thumbnail

        val success = viewModel.addToFavorites(name, description, comics, series, stories, events, imageUrl)
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
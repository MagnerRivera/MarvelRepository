package com.example.marvelcharacters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelcharacters.databinding.ListCardCharacterBinding
import com.example.marvelcharacters.retrofit.MarvelCharacter

class CharacterAdapter(
    var characters: List<MarvelCharacter>,
    private val onItemClick: (MarvelCharacter, String) -> Unit,
    private val onFavoriteClick: (MarvelCharacter) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ListCardCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        val imageUrl = generateThumbnailUrl(character)

        holder.bind(character, imageUrl)
        holder.itemView.setOnClickListener { onItemClick(character, imageUrl) }

        holder.binding.favorite.setOnClickListener { onFavoriteClick(character) }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    private fun generateThumbnailUrl(character: MarvelCharacter): String {
        val path = character.thumbnail.path
        val extension = character.thumbnail.extension
        return "$path.$extension"
    }

    inner class CharacterViewHolder(val binding: ListCardCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: MarvelCharacter, imageUrl: String) {
            binding.apply {
                txtCharacterName.text = character.name
                Glide.with(itemView)
                    .load(imageUrl)
                    .into(imagePhoto)
            }
        }
    }
}
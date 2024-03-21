package com.example.marvelcharacters.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelcharacters.databinding.ListCardCharacterBinding
import com.example.marvelcharacters.room.FavoriteCharacter

class FavoriteCharacterAdapter(
    var characters: List<FavoriteCharacter>,
    private val onItemClick: (FavoriteCharacter) -> Unit
) : RecyclerView.Adapter<FavoriteCharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ListCardCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        val imageUrl = "${character.thumbnailPath}.${character.thumbnailExtension}"

        holder.bind(character, imageUrl)
        holder.itemView.setOnClickListener { onItemClick(character) }
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class CharacterViewHolder(private val binding: ListCardCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: FavoriteCharacter, imageUrl: String) {
            binding.apply {
                favorite.visibility = View.INVISIBLE
                txtCharacterName.text = character.name
                Glide.with(itemView)
                    .load(imageUrl)
                    .into(imagePhoto)
            }
        }
    }
}
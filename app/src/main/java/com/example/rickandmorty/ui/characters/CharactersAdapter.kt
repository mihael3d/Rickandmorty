package com.example.rickandmorty.ui.characters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmorty.model.Character

/**
 * Adapter for the list of characters.
 */
class CharactersAdapter: PagingDataAdapter<Character, CaracterViewHolder>(CHARACTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaracterViewHolder {
        return CaracterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CaracterViewHolder, position: Int) {
        val characterItem = getItem(position)
        if (characterItem != null) {
            holder.bind(characterItem)
        }
    }

    companion object{
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }

}
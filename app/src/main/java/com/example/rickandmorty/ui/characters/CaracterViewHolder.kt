package com.example.rickandmorty.ui.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.rickandmorty.R
import com.example.rickandmorty.model.Character
import com.google.android.material.imageview.ShapeableImageView


class CaracterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.name)
    private val location: TextView = view.findViewById(R.id.location)
    private val status: TextView = view.findViewById(R.id.status)
    private val picture: ShapeableImageView = view.findViewById(R.id.picture)

    private var character: Character? = null

    init {

    }

    fun bind(character: Character?) {
        if (character == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
        } else {
            showRepoData(character)
        }
    }

    private fun showRepoData(character: Character) {
        this.character = character
        name.text = character.name
        location.text = character.location.name
        status.text = character.status

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(name.getContext())
            .load(character.image)
            .transition(withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_moc)
            .into(picture);
    }

    companion object {
        fun create(parent: ViewGroup): CaracterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.charater_view_item, parent, false)
            return CaracterViewHolder(view)
        }
    }

}
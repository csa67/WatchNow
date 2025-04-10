package com.example.tmdb.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.databinding.FavMovieItemBinding
import com.example.tmdb.model.FavMovie

class FavMoviesAdapter: ListAdapter<FavMovie,FavMoviesAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(val binding: FavMovieItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavMovieItemBinding.inflate(inflater,parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)

        with(holder.binding) {
            txtFavMovieTitle.text = movie.name
            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterURL}")
                .into(imgFavItemPoster)
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<FavMovie>() {
        override fun areItemsTheSame(oldItem: FavMovie, newItem: FavMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavMovie, newItem: FavMovie): Boolean {
            return oldItem == newItem
        }

    }


}
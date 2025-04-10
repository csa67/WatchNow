package com.example.tmdb.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.databinding.MovieTitleBinding
import com.example.tmdb.model.Result
import com.example.tmdb.view.ItemClickListener

class PopularMoviesAdapter(
    private val listener: ItemClickListener
) : ListAdapter<Result, PopularMoviesAdapter.ViewHolder>(MovieDiffCallback) {
    inner class ViewHolder(val binding: MovieTitleBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        val binding = holder.binding

        binding.txtMovieTitle.text = movie.title
        Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(binding.imgPoster)

        holder.itemView.setOnClickListener{
            listener.onClick(holder.adapterPosition, movie)
        }
    }

    object MovieDiffCallback: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }
}
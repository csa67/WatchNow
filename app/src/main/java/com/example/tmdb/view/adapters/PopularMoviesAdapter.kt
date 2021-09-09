package com.example.tmdb.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.databinding.MovieTitleBinding
import com.example.tmdb.model.Result
import com.example.tmdb.view.ItemClickListener

class PopularMoviesAdapter(private var popularMoviesList: List<Result>, val listener: ItemClickListener): RecyclerView.Adapter<PopularMoviesAdapter.ViewHolder>() {

    fun update(list: List<Result>){
        this.popularMoviesList = list
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: MovieTitleBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieTitleBinding.inflate(inflater,parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(popularMoviesList[position]){
                binding.txtMovieTitle.text = this.title
                Glide.with(holder.itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${this.posterPath}")
                    .into(binding.imgPoster)

                holder.itemView.setOnClickListener() {

                    listener.onClick(adapterPosition,this)

                }
                }
            }
        }

    override fun getItemCount(): Int {
        return popularMoviesList.size
    }
}
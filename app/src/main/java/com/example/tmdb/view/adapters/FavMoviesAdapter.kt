package com.example.tmdb.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.databinding.FavMovieItemBinding
import com.example.tmdb.databinding.MovieTitleBinding
import com.example.tmdb.model.FavMovie

class FavMoviesAdapter(private var favMoviesList: List<FavMovie>): RecyclerView.Adapter<FavMoviesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: FavMovieItemBinding): RecyclerView.ViewHolder(binding.root)

    fun update(list: List<FavMovie>){
        this.favMoviesList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavMovieItemBinding.inflate(inflater,parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(favMoviesList[position]){
                with(binding){
                    txtFavMovieTitle.text = name
                    Glide.with(holder.itemView.context)
                        .load("https://image.tmdb.org/t/p/w500${posterURL}")
                        .into(imgFavItemPoster)

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return favMoviesList.size
    }


}
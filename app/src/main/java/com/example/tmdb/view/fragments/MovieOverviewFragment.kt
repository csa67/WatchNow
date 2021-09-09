package com.example.tmdb.view.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentMovieOverviewBinding
import com.example.tmdb.db.FavDataBase
import com.example.tmdb.model.FavMovie
import com.example.tmdb.repository.FavRepoImpl
import com.example.tmdb.view.adapters.FavMoviesAdapter
import com.example.tmdb.viewmodel.FavViewModel
import com.example.tmdb.viewmodel.FavViewModelFactory
import okhttp3.internal.notify
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieOverviewFragment() : Fragment(){


    val args: MovieOverviewFragmentArgs by navArgs()

    val database by lazy { FavDataBase.getDatabase(context) }
    val repository by lazy { FavRepoImpl(database.favDao()) }
    private lateinit var binding: FragmentMovieOverviewBinding

    private val favViewModel: FavViewModel by lazy {
        ViewModelProvider(this, FavViewModelFactory(repository)).get(FavViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieOverviewBinding.inflate(inflater,container,false)
        return binding.root


    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val movieTitle = args.movieTitle
        binding.txtItemTitle.text = movieTitle


        val movieBackdrop = args.moviePoster
        val url = "https://image.tmdb.org/t/p/w500${movieBackdrop}"
        Glide.with(view.context)
            .load(url)
            .into(binding.imgItemPoster)

        val description = args.description
        binding.txtitemDescription.text = description

        val adultRated = args.adult
        binding.txtItemAdult.visibility = if(adultRated=="true") {
            View.VISIBLE
        } else View.GONE

        val releaseDate = LocalDate.parse(args.releaseyear, DateTimeFormatter.ISO_DATE)
        binding.txtItemReleaseYear.text = releaseDate.year.toString()

        binding.txtItemRating.text = args.rating.toString()

        var addedToFav = true

        binding.btnAddFav.setOnCheckedChangeListener{_, isChecked->
            if(isChecked){
                Toast.makeText(context,"Added to favorites", Toast.LENGTH_SHORT).show()

                favViewModel.addFavMovie(
                    FavMovie(
                        name = movieTitle,
                        posterURL = movieBackdrop
                    )
                )
            }else{
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                favViewModel.delFavMovie(
                    FavMovie(
                        name = movieTitle,
                        posterURL = movieBackdrop
                    )
                )

            }


        }


          /*  //if(drawablecur.pixelsEqualTo(drawable1)){
            if(!addedToFav){
               // binding.btnAddFav.setImageResource(R.drawable.ic_added_favorite)
                Toast.makeText(context,"Added to favorites", Toast.LENGTH_SHORT).show()

                favViewModel.addFavMovie(
                    FavMovie(
                        name = movieTitle,
                        posterURL = movieBackdrop

                    )
                )

            } else {
                //binding.btnAddFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
            }}*/

    }}

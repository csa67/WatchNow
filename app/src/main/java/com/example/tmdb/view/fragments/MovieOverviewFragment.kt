package com.example.tmdb.view.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentMovieOverviewBinding
import com.example.tmdb.db.FavDataBase
import com.example.tmdb.model.FavMovie
import com.example.tmdb.repository.FavRepoImpl
import com.example.tmdb.viewmodel.FavViewModel
import com.example.tmdb.viewmodel.FavViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieOverviewFragment : Fragment() {

    private val args: MovieOverviewFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieOverviewBinding
    private lateinit var favViewModel: FavViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FavDataBase.getDatabase(requireContext())
        val repository = FavRepoImpl(database.favDao())
        favViewModel = ViewModelProvider(this, FavViewModelFactory(repository))[FavViewModel::class.java]

        val movieTitle = args.movieTitle
        val movieBackdrop = args.moviePoster
        val description = args.description
        val adultRated = args.adult
        val rating = args.rating

        binding.txtItemTitle.text = movieTitle

        val imageUrl = "https://image.tmdb.org/t/p/w500$movieBackdrop"
        Glide.with(view.context)
            .load(imageUrl)
            .into(binding.imgItemPoster)

        binding.txtitemDescription.text = description
        binding.txtItemAdult.visibility = if (adultRated == "true") View.VISIBLE else View.GONE

        val releaseYear = try {
            LocalDate.parse(args.releaseyear, DateTimeFormatter.ISO_DATE).year.toString()
        } catch (e: Exception) {
            "Unknown"
        }
        binding.txtItemReleaseYear.text = releaseYear
        binding.txtItemRating.text = rating.toString()

        binding.btnAddFav.setOnCheckedChangeListener { _, isChecked ->
            System.out.println("Name $movieTitle and poster $movieBackdrop")
            val favMovie = FavMovie(name = movieTitle, posterURL = movieBackdrop)
            if (isChecked) {
                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                favViewModel.addFavMovie(favMovie)
            } else {
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                favViewModel.delFavMovie(favMovie)
            }
        }
    }

}

package com.example.tmdb.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tmdb.databinding.FragmentMovieOverviewBinding
import com.example.tmdb.db.FavDataBase
import com.example.tmdb.model.FavMovie
import com.example.tmdb.model.Movie
import com.example.tmdb.repository.FavRepoImpl
import com.example.tmdb.viewmodel.FavViewModel
import com.example.tmdb.viewmodel.FavViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieOverviewFragment : Fragment() {

    private val args: MovieOverviewFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieOverviewBinding

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
        val favViewModel: FavViewModel = ViewModelProvider(this, FavViewModelFactory(repository))[FavViewModel::class.java]
        val movie: Movie = args.movie

        val releaseYear = try {
            LocalDate.parse(movie.releaseYear, DateTimeFormatter.ISO_DATE).year.toString()
        } catch (e: Exception) {
            "Unknown"
        }

        binding.txtItemTitle.text = movie.title
        binding.txtitemDescription.text = movie.description
        binding.txtItemAdult.visibility = if (movie.adult) View.VISIBLE else View.GONE
        binding.txtItemReleaseYear.text = releaseYear
        binding.txtItemRating.text = movie.rating.toString()

        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster}"
        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.imgItemPoster)

        binding.btnAddFav.setOnCheckedChangeListener { _, isChecked ->
            val favMovie = FavMovie(name = movie.title, posterURL = movie.poster)
            if (isChecked) {
                favViewModel.addFavMovie(favMovie)
            } else {
                favViewModel.delFavMovie(favMovie)
            }
        }

        favViewModel.toastMessage.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
        }
    }


}

package com.example.tmdb.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tmdb.databinding.FragmentMovieOverviewBinding
import com.example.tmdb.db.FavDataBase
import com.example.tmdb.model.FavMovie
import com.example.tmdb.model.Movie
import com.example.tmdb.networking.RetrofitBuilder
import com.example.tmdb.repository.FavRepoImpl
import com.example.tmdb.repository.MovieRepoImpl
import com.example.tmdb.viewmodel.FavViewModel
import com.example.tmdb.viewmodel.FavViewModelFactory
import com.example.tmdb.viewmodel.MovieViewModel
import com.example.tmdb.viewmodel.MovieViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieOverviewFragment : Fragment() {

    private val args: MovieOverviewFragmentArgs by navArgs()
    private var _binding: FragmentMovieOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by lazy {
            ViewModelProvider(
                this,
                FavViewModelFactory(FavRepoImpl(FavDataBase.getDatabase(requireContext()).favDao()))
            )[FavViewModel::class.java]
        }

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

        val favCheckListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            val favMovie = FavMovie(name = args.movie.title, posterURL = args.movie.poster)
            if (isChecked) {
                viewModel.addFavMovie(favMovie)
            } else {
                val matchedItem = viewModel.favMoviesList.value?.find { it.name == movie.title }
                if (matchedItem != null) {
                    viewModel.delFavMovie(matchedItem)
                }
            }
        }

        viewModel.favMoviesList.observe(viewLifecycleOwner) {
            val isAlreadyFavorite = it.any { m -> m.name == movie.title }
            binding.btnAddFav.setOnCheckedChangeListener(null)
            binding.btnAddFav.isChecked = isAlreadyFavorite
            binding.btnAddFav.setOnCheckedChangeListener(favCheckListener,)
        }

        viewModel.toastMessage.observe(viewLifecycleOwner){toastMsg ->
            toastMsg?.let{
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
                viewModel.clearToastMessage();
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}

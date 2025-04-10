package com.example.tmdb.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.model.Movie
import com.example.tmdb.model.Response
import com.example.tmdb.model.Result
import com.example.tmdb.networking.RetrofitBuilder
import com.example.tmdb.repository.MovieRepoImpl
import com.example.tmdb.view.ItemClickListener
import com.example.tmdb.view.adapters.PopularMoviesAdapter
import com.example.tmdb.viewmodel.MovieViewModel
import com.example.tmdb.viewmodel.MovieViewModelFactory
import java.util.Collections.emptyList

class HomeFragment : Fragment(),ItemClickListener {

   private lateinit var binding: FragmentHomeBinding

   private val viewModel: MovieViewModel by viewModels {
       val apiService = RetrofitBuilder.apiService
       MovieViewModelFactory(MovieRepoImpl(apiService))
   }

    private lateinit var adapter: PopularMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PopularMoviesAdapter(emptyList(),this)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(requireContext(),3)

        viewModel.moviesResponse.observe(viewLifecycleOwner
        ) { response ->
           when(response){
               is Response.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE;
               }
               is Response.Success -> {

                   binding.progressBar.visibility = View.GONE
                   val movieList = response.data
                   movieList.results?.let { adapter.update(it) }
               }
               is Response.Error ->{
                   val message = response.errorMessage ?: "Something went wrong"
                   Toast.makeText(context,message,Toast.LENGTH_LONG).show()
               }
           }
        }
    }

    override fun onClick(position: Int, result: Result) {

        val movie = Movie(
            poster = result.backdropPath?: "",
            adult = result.adult ?: false,
            title = result.title ?: "Untitled",
            releaseYear = result.releaseDate ?: "Unknown",
            description = result.overview ?: "No overview available",
            rating = result.voteAverage?.toFloat() ?: 0.0f,
            isFav = false
        )
        val action = HomeFragmentDirections.actionHomeFragmentToMovieOverviewFragment(movie)
        findNavController().navigate(action)
    }




}
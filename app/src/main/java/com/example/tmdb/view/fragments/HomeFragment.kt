package com.example.tmdb.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.databinding.FragmentHomeBinding
import com.example.tmdb.model.Result
import com.example.tmdb.view.ItemClickListener
import com.example.tmdb.view.adapters.PopularMoviesAdapter
import com.example.tmdb.viewmodel.MovieViewModel
import com.example.tmdb.viewmodel.MovieViewModelFactory

class HomeFragment : Fragment(),ItemClickListener {

    companion object{
        fun newInstance() = HomeFragment()
    }

   private lateinit var binding: FragmentHomeBinding

   private val viewModel: MovieViewModel by lazy {
       ViewModelProvider(this, MovieViewModelFactory()).get(MovieViewModel::class.java)
   }

    private var adapter: PopularMoviesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PopularMoviesAdapter(emptyList(),this)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(requireContext(),3)

        viewModel.movie.observe(viewLifecycleOwner,
            {movie ->
                movie.let { list->
                   adapter?.update(list.results!!)

                }

            }

        )




    }

    override fun onClick(position: Int, result: Result) {

        val action = HomeFragmentDirections
                    .actionHomeFragmentToMovieOverviewFragment(
                        result.title!!,
                        result.backdropPath!!,
                        result.overview!!,
                        result.adult.toString()!!,
                        result.releaseDate!!,
                        result.voteAverage?.toFloat()!!
                    )
        findNavController().navigate(action)
    }




}
package com.example.tmdb.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdb.databinding.FragmentFavouritesBinding
import com.example.tmdb.db.FavDataBase
import com.example.tmdb.repository.FavRepoImpl
import com.example.tmdb.view.adapters.FavMoviesAdapter
import com.example.tmdb.viewmodel.FavViewModel
import com.example.tmdb.viewmodel.FavViewModelFactory


class FavouritesFragment : Fragment(){

    companion object{
        fun newInstance() = FavouritesFragment()
    }


    val database by lazy { FavDataBase.getDatabase(context) }
    val repository by lazy { FavRepoImpl(database.favDao()) }

    private lateinit var binding: FragmentFavouritesBinding
    private var adapter: FavMoviesAdapter? = null
    private val favViewModel: FavViewModel by lazy {
        ViewModelProvider(this, FavViewModelFactory(repository)).get(FavViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavMoviesAdapter(emptyList())
        binding.rvFav.adapter = adapter
        binding.rvFav.layoutManager = LinearLayoutManager(context)

        favViewModel.favMoviesList.observe(viewLifecycleOwner,
            {favMovie ->
                favMovie.let {
                    adapter?.update(it!!)
                }
            }

        )


    }

    }

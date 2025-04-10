package com.example.tmdb.view.fragments

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.FragmentFavouritesBinding
import com.example.tmdb.db.FavDataBase
import com.example.tmdb.repository.FavRepoImpl
import com.example.tmdb.view.adapters.FavMoviesAdapter
import com.example.tmdb.viewmodel.FavViewModel
import com.example.tmdb.viewmodel.FavViewModelFactory
import java.util.Collections.emptyList


class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val database by lazy { requireContext().let { FavDataBase.getDatabase(it) } }
    private val repository by lazy { FavRepoImpl(database.favDao()) }

    private val adapter by lazy { FavMoviesAdapter() }

    private val favViewModel: FavViewModel by viewModels {
        FavViewModelFactory(repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFav.adapter = adapter
        binding.rvFav.layoutManager = LinearLayoutManager(context)

        favViewModel.favMoviesList.observe(viewLifecycleOwner) { updatedList ->
            adapter.submitList(updatedList)
        }

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val movie = adapter.currentList[itemPosition]
                favViewModel.delFavMovie(movie)

                Toast.makeText(
                    context,
                    "Deleted ${movie.name} from favorites",
                    Toast.LENGTH_SHORT
                ).show();
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.rvFav)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}

package com.rasyidin.myfilmlist.ui.feature.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.databinding.FragmentFavMoviesBinding
import com.rasyidin.myfilmlist.ui.adapter.movie.FavMoviesAdapter
import com.rasyidin.myfilmlist.ui.base.BaseFragment
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.MOVIE_CODE
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.MOVIE_ID
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.MOVIE_TYPE
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavMoviesFragment :
    BaseFragment<FragmentFavMoviesBinding>(FragmentFavMoviesBinding::inflate) {

    private val viewModel: FavoriteViewModel by viewModel()

    private val favAdapter by lazy { FavMoviesAdapter { navigateToDetail(it.id) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val navBar =
                (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            navBar.visibility = View.GONE

            setupRecyclerView()

            observeFavMovies()
        }
    }

    override fun onResume() {
        super.onResume()
        val navBar =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE
    }

    private fun navigateToDetail(movieId: Int) {
        val args = Bundle().apply {
            putInt(MOVIE_ID, movieId)
            putInt(MOVIE_TYPE, MOVIE_CODE)
        }
        findNavController().navigate(
            R.id.action_favoriteFragment_to_detailActivity,
            args
        )
    }

    private fun setupRecyclerView() = binding.rvFav.apply {
        adapter = favAdapter
        layoutManager = GridLayoutManager(activity, 3)
        setHasFixedSize(true)
    }

    private fun observeFavMovies() {
        viewModel.getFavMovie().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.lottieNoData.visibility = View.VISIBLE
                binding.rvFav.visibility = View.GONE
            } else {
                binding.rvFav.visibility = View.VISIBLE
                binding.lottieNoData.visibility = View.GONE
                favAdapter.submitList(it)
            }
        }
    }

}
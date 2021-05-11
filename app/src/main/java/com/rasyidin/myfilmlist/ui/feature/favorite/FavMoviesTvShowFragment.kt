package com.rasyidin.myfilmlist.ui.feature.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.databinding.FragmentFavMoviesTvShowBinding
import com.rasyidin.myfilmlist.ui.adapter.movie.FavMoviesAdapter
import com.rasyidin.myfilmlist.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavMoviesTvShowFragment :
    BaseFragment<FragmentFavMoviesTvShowBinding>(FragmentFavMoviesTvShowBinding::inflate) {

    private val viewModel: FavoriteViewModel by viewModel()

    private val favAdapter by lazy { FavMoviesAdapter { navigateToDetail(it) } }

    private fun navigateToDetail(movie: Movie) {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val navBar =
                (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            navBar.visibility = View.GONE

            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() = binding.rvFav.apply {
        adapter = favAdapter
        layoutManager = GridLayoutManager(activity, 3)
        setHasFixedSize(true)
    }

    private fun observeFavMovies() {
        viewModel.getFavMovie().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) binding.lottieNoData.visibility = View.VISIBLE
            else {
                binding.rvFav.visibility = View.VISIBLE
                favAdapter.submitList(it)
            }
        }
    }

    companion object {

        private const val ARG_SECTION_INDEX = "section_index"

        fun newInstance(index: Int) =
            FavMoviesTvShowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_INDEX, index)
                }
            }
    }
}
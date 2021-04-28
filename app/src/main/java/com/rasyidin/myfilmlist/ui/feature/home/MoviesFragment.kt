package com.rasyidin.myfilmlist.ui.feature.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.databinding.FragmentMoviesBinding
import com.rasyidin.myfilmlist.ui.adapter.movie.MovieNowPlayingAdapter
import com.rasyidin.myfilmlist.ui.adapter.movie.MoviePopularAdapter
import com.rasyidin.myfilmlist.ui.adapter.movie.MovieTopRatedAdapter
import com.rasyidin.myfilmlist.ui.adapter.movie.MovieUpComingAdapter
import com.rasyidin.myfilmlist.ui.base.BaseFragment
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.MOVIE_CODE
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.MOVIE_ID
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.MOVIE_TYPE
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {

    private val viewModel: MoviesViewModel by viewModel()

    private lateinit var nowPlayingAdapter: MovieNowPlayingAdapter
    private lateinit var popularAdapter: MoviePopularAdapter
    private lateinit var upComingAdapter: MovieUpComingAdapter
    private lateinit var topRatedAdapter: MovieTopRatedAdapter

    private lateinit var behavior: BottomSheetBehavior<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val navBar =
                (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            navBar.visibility = View.VISIBLE

            setupBottomSheet()

            setupNowPlayingAdapter()
            setupPopularAdapter()
            setupComingSoonAdapter()
            setupTopRatedAdapter()

            subscribeToObserver()

            onItemClicked()

            navigateToSearchMovies()
        }
    }

    private fun navigateToSearchMovies() {
        binding.dashboardContainer.fabSearch.setOnClickListener {
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesFragmentToSearchMovieFragment()
            )
        }
    }

    private fun subscribeToObserver() {
        viewModel.getNowPlaying.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.dashboardContainer.apply {
                        rvNowPlaying.visibility = View.VISIBLE
                        shimmerLoading.visibility = View.GONE
                    }
                    showNowPlaying(resource)
                }

                is Resource.Loading -> {
                    binding.dashboardContainer.apply {
                        shimmerLoading.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.dashboardContainer.apply {
                        rvNowPlaying.visibility = View.VISIBLE
                        shimmerLoading.visibility = View.GONE
                    }
                    Toast.makeText(
                        activity,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.getPopular.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.botSheetContainer.loadingPopular.visibility = View.GONE
                    binding.botSheetContainer.rvPopular.visibility = View.VISIBLE
                    showPopular(resource)
                }
                is Resource.Loading -> {
                    binding.botSheetContainer.apply {
                        loadingPopular.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.botSheetContainer.loadingPopular.visibility = View.GONE
                    binding.botSheetContainer.rvPopular.visibility = View.VISIBLE
                    Toast.makeText(
                        activity,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.getUpComing.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.botSheetContainer.loadingComingSoon.visibility = View.GONE
                    binding.botSheetContainer.rvComingSoon.visibility = View.VISIBLE
                    showUpComing(resource)
                }
                is Resource.Loading -> {
                    binding.botSheetContainer.apply {
                        loadingComingSoon.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.botSheetContainer.loadingComingSoon.visibility = View.GONE
                    binding.botSheetContainer.rvComingSoon.visibility = View.VISIBLE
                    Toast.makeText(
                        activity,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.getTopRated.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.botSheetContainer.loadingTopRated.visibility = View.GONE
                    binding.botSheetContainer.rvTopRated.visibility = View.VISIBLE
                    showTopRated(resource)
                }
                is Resource.Loading -> {
                    binding.botSheetContainer.apply {
                        loadingTopRated.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.botSheetContainer.loadingTopRated.visibility = View.GONE
                    binding.botSheetContainer.rvTopRated.visibility = View.VISIBLE
                    Toast.makeText(
                        activity,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun onItemClicked() {
        nowPlayingAdapter.onItemClickListener = { selectedItem ->
            navigateToDetail(selectedItem.id)
        }

        popularAdapter.onItemClickListener = { selectedItem ->
            navigateToDetail(selectedItem.id)
        }

        upComingAdapter.onItemClickListener = { selectedItem ->
            navigateToDetail(selectedItem.id)
        }

        topRatedAdapter.onItemClickListener = { selectedItem ->
            navigateToDetail(selectedItem.id)
        }
    }

    private fun navigateToDetail(movieId: Int) {
        val args = Bundle().apply {
            putInt(MOVIE_ID, movieId)
            putInt(MOVIE_TYPE, MOVIE_CODE)
        }
        findNavController().navigate(
            R.id.action_moviesFragment_to_detailActivity,
            args
        )
    }

    private fun showTopRated(resource: Resource<List<Movie>>) {
        if (resource.data.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.movie_not_found), Toast.LENGTH_SHORT).show()
        } else {
            resource.data.let {
                topRatedAdapter.setData(it)
            }
        }
    }

    private fun showUpComing(resource: Resource<List<Movie>>) {
        if (resource.data.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.movie_not_found), Toast.LENGTH_SHORT).show()
        } else {
            resource.data.let {
                upComingAdapter.setData(it)
            }
        }
    }

    private fun showNowPlaying(resource: Resource<List<Movie>>) {
        if (resource.data.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.movie_not_found), Toast.LENGTH_SHORT).show()
        } else {
            resource.data.let {
                nowPlayingAdapter.setData(it)
            }
        }
    }

    private fun showPopular(resource: Resource<List<Movie>>) {
        if (resource.data.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.movie_not_found), Toast.LENGTH_SHORT).show()
        } else {
            resource.data.let {
                popularAdapter.setData(it)
            }
        }
    }

    private fun setupNowPlayingAdapter() = binding.dashboardContainer.rvNowPlaying.apply {
        nowPlayingAdapter = MovieNowPlayingAdapter()
        adapter = nowPlayingAdapter
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupPopularAdapter() = binding.botSheetContainer.rvPopular.apply {
        popularAdapter = MoviePopularAdapter()
        adapter = popularAdapter
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupComingSoonAdapter() = binding.botSheetContainer.rvComingSoon.apply {
        upComingAdapter = MovieUpComingAdapter()
        adapter = upComingAdapter
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupTopRatedAdapter() = binding.botSheetContainer.rvTopRated.apply {
        topRatedAdapter = MovieTopRatedAdapter()
        adapter = topRatedAdapter
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupBottomSheet() {
        behavior = BottomSheetBehavior.from(binding.botSheetContainer.bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.dashboardContainer?.rvNowPlaying?.adapter = null
        _binding?.botSheetContainer?.apply {
            rvPopular.adapter = null
            rvComingSoon.adapter = null
            rvTopRated.adapter = null
        }
    }
}
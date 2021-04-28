package com.rasyidin.myfilmlist.ui.feature.tvshow

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
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.databinding.FragmentTvShowBinding
import com.rasyidin.myfilmlist.ui.adapter.tv.TvAiringTodayAdapter
import com.rasyidin.myfilmlist.ui.adapter.tv.TvOnTheAirAdapter
import com.rasyidin.myfilmlist.ui.adapter.tv.TvPopularAdapter
import com.rasyidin.myfilmlist.ui.adapter.tv.TvTopRatedAdapter
import com.rasyidin.myfilmlist.ui.base.BaseFragment
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.TV_SHOW_CODE
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.TV_SHOW_ID
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.TV_SHOW_TYPE
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : BaseFragment<FragmentTvShowBinding>(FragmentTvShowBinding::inflate) {

    private val viewModel: TvShowViewModel by viewModel()

    private lateinit var airingTodayAdapter: TvAiringTodayAdapter
    private lateinit var onTheAirAdapter: TvOnTheAirAdapter
    private lateinit var popularAdapter: TvPopularAdapter
    private lateinit var topRatedAdapter: TvTopRatedAdapter

    private lateinit var behavior: BottomSheetBehavior<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val navBar =
                (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            navBar.visibility = View.VISIBLE

            setupBottomSheet()

            setupAiringTodayAdapter()
            setupOnTheAirAdapter()
            setupPopularAdapter()
            setupTopRatedAdapter()

            subscribeToObserver()

            onItemClicked()

            navigateToSearchTvShow()
        }
    }

    private fun navigateToSearchTvShow() {
        binding.dashboardContainer.fabSearch.setOnClickListener {
            findNavController().navigate(
                TvShowFragmentDirections.actionTvShowFragmentToSearchTvShowFragment()
            )
        }
    }

    private fun subscribeToObserver() {
        viewModel.getAiringToday.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.dashboardContainer.apply {
                        rvNowPlaying.visibility = View.VISIBLE
                        shimmerLoading.visibility = View.GONE
                    }
                    showAiringToday(resource)
                }
                is Resource.Loading -> {
                    binding.dashboardContainer.apply {
                        shimmerLoading.visibility = View.VISIBLE
                        rvNowPlaying.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    binding.dashboardContainer.apply {
                        rvNowPlaying.visibility = View.VISIBLE
                        shimmerLoading.visibility = View.GONE
                    }
                    Toast.makeText(
                        activity,
                        "Something Wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.getPopular.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.botSheetContainer.apply {
                        rvPopular.visibility = View.VISIBLE
                        loadingPopular.visibility = View.GONE
                    }
                    showPopular(resource)
                }
                is Resource.Loading -> {
                    binding.botSheetContainer.apply {
                        loadingPopular.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.botSheetContainer.apply {
                        rvPopular.visibility = View.VISIBLE
                        loadingPopular.visibility = View.GONE
                    }
                    Toast.makeText(
                        activity,
                        "Something Wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.getOnTheAir.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.botSheetContainer.apply {
                        rvComingSoon.visibility = View.VISIBLE
                        loadingComingSoon.visibility = View.GONE
                    }
                    showOnTheAir(resource)
                }
                is Resource.Loading -> {
                    binding.botSheetContainer.apply {
                        loadingComingSoon.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.botSheetContainer.apply {
                        rvComingSoon.visibility = View.VISIBLE
                        loadingComingSoon.visibility = View.GONE
                    }
                    Toast.makeText(
                        activity,
                        "Something Wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.getTopRated.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.botSheetContainer.apply {
                        rvTopRated.visibility = View.VISIBLE
                        loadingTopRated.visibility = View.GONE
                    }
                    showTopRated(resource)
                }
                is Resource.Loading -> {
                    binding.botSheetContainer.apply {
                        loadingTopRated.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.botSheetContainer.apply {
                        rvTopRated.visibility = View.VISIBLE
                        loadingTopRated.visibility = View.GONE
                    }
                    Toast.makeText(
                        activity,
                        "Something Wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun onItemClicked() {
        topRatedAdapter.onItemClickListener = { selectedItem ->
            navigateToDetail(selectedItem.id)
        }

        onTheAirAdapter.onItemClickListener = { selectedItem ->
            navigateToDetail(selectedItem.id)
        }

        popularAdapter.onItemClickListener = { selectedItem ->
            navigateToDetail(selectedItem.id)
        }

        airingTodayAdapter.onItemClickListener = { selectedItem ->
            navigateToDetail(selectedItem.id)
        }
    }

    private fun navigateToDetail(tvShowId: Int) {
        val args = Bundle().apply {
            putInt(TV_SHOW_ID, tvShowId)
            putInt(TV_SHOW_TYPE, TV_SHOW_CODE)
        }
        findNavController().navigate(
            R.id.action_tvShowFragment_to_detailActivity,
            args
        )
    }

    private fun showTopRated(resource: Resource<List<TvShow>>) {
        if (resource.data.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.tv_show_not_found), Toast.LENGTH_SHORT).show()
        } else {
            resource.data.let {
                topRatedAdapter.setData(it)
            }
        }
    }

    private fun showOnTheAir(resource: Resource<List<TvShow>>) {
        if (resource.data.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.tv_show_not_found), Toast.LENGTH_SHORT).show()
        } else {
            resource.data.let {
                onTheAirAdapter.setData(it)
            }
        }
    }

    private fun showAiringToday(resource: Resource<List<TvShow>>) {
        if (resource.data.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.tv_show_not_found), Toast.LENGTH_SHORT).show()
        } else {
            resource.data.let {
                airingTodayAdapter.setData(it)
            }
        }
    }

    private fun showPopular(resource: Resource<List<TvShow>>) {
        if (resource.data.isNullOrEmpty()) {
            Toast.makeText(activity, getString(R.string.tv_show_not_found), Toast.LENGTH_SHORT).show()
        } else {
            resource.data.let {
                popularAdapter.setData(it)
            }
        }
    }

    private fun setupAiringTodayAdapter() = binding.dashboardContainer.rvNowPlaying.apply {
        binding.dashboardContainer.textNowPlaying.text = getString(R.string.airing_today)
        airingTodayAdapter = TvAiringTodayAdapter()
        adapter = airingTodayAdapter
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
    }

    private fun setupOnTheAirAdapter() = binding.botSheetContainer.rvComingSoon.apply {
        binding.botSheetContainer.textComingSoon.text = getString(R.string.on_the_air)
        onTheAirAdapter = TvOnTheAirAdapter()
        adapter = onTheAirAdapter
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
    }

    private fun setupPopularAdapter() = binding.botSheetContainer.rvPopular.apply {
        popularAdapter = TvPopularAdapter()
        adapter = popularAdapter
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
    }

    private fun setupTopRatedAdapter() = binding.botSheetContainer.rvTopRated.apply {
        topRatedAdapter = TvTopRatedAdapter()
        adapter = topRatedAdapter
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
    }

    private fun setupBottomSheet() {
        behavior = BottomSheetBehavior.from(binding.botSheetContainer.bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.dashboardContainer?.rvNowPlaying?.adapter = null
        _binding?.botSheetContainer?.apply {
            rvTopRated.adapter = null
            rvComingSoon.adapter = null
            rvPopular.adapter = null
        }
    }
}
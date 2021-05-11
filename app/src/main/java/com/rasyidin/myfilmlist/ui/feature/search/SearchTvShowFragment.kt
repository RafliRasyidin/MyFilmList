package com.rasyidin.myfilmlist.ui.feature.search

import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.databinding.FragmentSearchTvShowBinding
import com.rasyidin.myfilmlist.ui.adapter.tv.SearchTvAdapter
import com.rasyidin.myfilmlist.ui.base.BaseFragment
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.TV_SHOW_CODE
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.TV_SHOW_ID
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.TV_SHOW_TYPE
import com.rasyidin.myfilmlist.ui.helper.Constants.ANIMATION_DURATION_TWO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ObsoleteCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class SearchTvShowFragment :
    BaseFragment<FragmentSearchTvShowBinding>(FragmentSearchTvShowBinding::inflate) {

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var searchAdapter: SearchTvAdapter

    private lateinit var bounds: ChangeBounds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(activity).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementEnterTransition = enterTransition()

        sharedElementReturnTransition = animation
        sharedElementReturnTransition = returnTransition()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val navBar =
                (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            navBar.visibility = View.GONE

            setupRecyclerView()

            searchTvShow()

            subscribeToObserver()

            onItemClicked()

            onBackClicked()
        }
    }

    private fun enterTransition(): Transition {
        bounds = ChangeBounds()
        bounds.duration = ANIMATION_DURATION_TWO

        return bounds
    }

    private fun returnTransition(): Transition {
        bounds = ChangeBounds()
        bounds.apply {
            interpolator = DecelerateInterpolator()
            duration = ANIMATION_DURATION_TWO
        }

        return bounds
    }

    private fun onItemClicked() {
        searchAdapter.onItemClickListener = { selectedItem ->
            val args = Bundle().apply {
                putInt(TV_SHOW_ID, selectedItem.id)
                putInt(TV_SHOW_TYPE, TV_SHOW_CODE)
            }
            findNavController().navigate(
                R.id.action_searchTvShowFragment_to_detailActivity,
                args
            )
        }
    }

    private fun searchTvShow() {
        binding.searchView.onActionViewExpanded()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(newText)
                }
                return true
            }
        })
    }

    private fun onBackClicked() {
        binding.imgBack.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.searchView to getString(R.string.transitionFabSearch)
            )
            findNavController().navigate(
                R.id.action_searchTvShowFragment_to_tvShowFragment,
                null,
                null,
                extras
            )
        }
    }

    private fun subscribeToObserver() {
        viewModel.searchTvShow.observe(viewLifecycleOwner) { tvShow ->
            tvShow.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Success -> {
                        binding.loading.visibility = View.GONE
                        binding.lottieError.visibility = View.GONE
                        resource.data?.let { data ->
                            if (data.isNotEmpty()) {
                                searchAdapter.setData(data)
                                binding.lottieNoData.visibility = View.GONE
                                binding.rvSearch.visibility = View.VISIBLE
                            } else {
                                binding.lottieNoData.visibility = View.VISIBLE
                                binding.rvSearch.visibility = View.GONE
                            }
                        }

                    }
                    is Resource.Error -> {
                        binding.loading.visibility = View.GONE
                        binding.lottieError.visibility = View.VISIBLE
                        binding.lottieNoData.visibility = View.GONE
                        binding.rvSearch.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                        binding.lottieError.visibility = View.GONE
                        binding.lottieNoData.visibility = View.GONE
                        binding.rvSearch.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.rvSearch.apply {
        searchAdapter = SearchTvAdapter()
        adapter = searchAdapter
        layoutManager = GridLayoutManager(activity, 3)
        setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.rvSearch?.adapter = null
    }

}
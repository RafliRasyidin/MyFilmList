package com.rasyidin.myfilmlist.ui.feature.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.databinding.FragmentFavTvShowBinding
import com.rasyidin.myfilmlist.ui.adapter.tv.FavTvShowAdapter
import com.rasyidin.myfilmlist.ui.base.BaseFragment
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.TV_SHOW_CODE
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.TV_SHOW_ID
import com.rasyidin.myfilmlist.ui.feature.detail.DetailActivity.Companion.TV_SHOW_TYPE
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavTvShowFragment :
    BaseFragment<FragmentFavTvShowBinding>(FragmentFavTvShowBinding::inflate) {

    private val viewModel: FavoriteViewModel by viewModel()

    private val favAdapter by lazy { FavTvShowAdapter { navigateToDetail(it.id) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            setupRecyclerView()

            observeFavTvShow()
        }
    }

    override fun onResume() {
        super.onResume()
        val navBar =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE
    }

    private fun navigateToDetail(tvId: Int) {
        val args = Bundle().apply {
            putInt(TV_SHOW_ID, tvId)
            putInt(TV_SHOW_TYPE, TV_SHOW_CODE)
        }
        findNavController().navigate(
            R.id.action_favoriteFragment_to_detailActivity,
            args
        )
    }

    private fun observeFavTvShow() {
        viewModel.getFavTvShow().observe(viewLifecycleOwner) { data ->
            if (data.isNullOrEmpty()) {
                binding.lottieNoData.visibility = View.VISIBLE
                binding.rvFavTvShow.visibility = View.GONE
            } else {
                binding.lottieNoData.visibility = View.GONE
                binding.rvFavTvShow.visibility = View.VISIBLE
                favAdapter.submitList(data)
            }
        }
    }

    private fun setupRecyclerView() = binding.rvFavTvShow.apply {
        adapter = favAdapter
        layoutManager = GridLayoutManager(activity, 3)
        setHasFixedSize(true)
    }
}
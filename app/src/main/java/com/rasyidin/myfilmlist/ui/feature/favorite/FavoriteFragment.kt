package com.rasyidin.myfilmlist.ui.feature.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.databinding.FragmentFavoriteBinding
import com.rasyidin.myfilmlist.ui.adapter.SectionPagerAdapter
import com.rasyidin.myfilmlist.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val viewModel: FavoriteViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val navBar =
                (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            navBar.visibility = View.VISIBLE

            initViewPager()
        }
    }

    private fun initViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(childFragmentManager, lifecycle)
        binding.vpFav.apply {
            adapter = sectionPagerAdapter
            offscreenPageLimit = 2
        }
    }

}
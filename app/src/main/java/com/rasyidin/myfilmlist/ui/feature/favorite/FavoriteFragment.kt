package com.rasyidin.myfilmlist.ui.feature.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.databinding.FragmentFavoriteBinding
import com.rasyidin.myfilmlist.ui.adapter.SectionPagerAdapter
import com.rasyidin.myfilmlist.ui.adapter.SectionPagerAdapter.Companion.TAB_TITLES
import com.rasyidin.myfilmlist.ui.base.BaseFragment

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private var mediator: TabLayoutMediator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val navBar =
                (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            navBar.visibility = View.VISIBLE

            initViewPager()

            initTabLayout()
        }
    }

    private fun initViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(childFragmentManager, lifecycle)
        binding.vpFav.apply {
            adapter = sectionPagerAdapter
            offscreenPageLimit = 2
        }
    }

    private fun initTabLayout() {
        mediator = TabLayoutMediator(binding.tabsFav, binding.vpFav) { tab, pos ->
            tab.text = when (pos) {
                0 -> getString(TAB_TITLES[0])
                else -> getString(TAB_TITLES[1])
            }
        }
        mediator?.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
        mediator = null
    }

}
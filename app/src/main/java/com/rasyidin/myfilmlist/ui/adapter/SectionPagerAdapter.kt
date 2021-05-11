package com.rasyidin.myfilmlist.ui.adapter

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.ui.feature.favorite.FavMoviesTvShowFragment

class SectionPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.movies, R.string.tv_show)
    }

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavMoviesTvShowFragment.newInstance(
                position
            )
            else -> Fragment()
        }
    }
}
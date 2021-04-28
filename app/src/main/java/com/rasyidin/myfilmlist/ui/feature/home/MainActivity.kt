package com.rasyidin.myfilmlist.ui.feature.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.databinding.ActivityMainBinding
import com.rasyidin.myfilmlist.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navHostFragment: NavController

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = Navigation.findNavController(this, R.id.filmNavHostFragment)
        binding.bottomNavigationView.setupWithNavController(navHostFragment)
    }
}
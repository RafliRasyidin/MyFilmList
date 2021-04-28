package com.rasyidin.myfilmlist.ui.feature

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rasyidin.myfilmlist.databinding.ActivitySplashBinding
import com.rasyidin.myfilmlist.ui.base.BaseActivity
import com.rasyidin.myfilmlist.ui.feature.home.MainActivity
import com.rasyidin.myfilmlist.ui.helper.Constants.ANIMATION_DURATION_ONE
import com.rasyidin.myfilmlist.ui.helper.Constants.ANIMATION_DURATION_TWO
import com.rasyidin.myfilmlist.ui.helper.Constants.SPLASH_DELAY
import com.rasyidin.myfilmlist.ui.helper.Constants.START_OFFSET
import com.rasyidin.myfilmlist.ui.helper.slideUp

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashDelay()

        binding.imgFilm.slideUp(ANIMATION_DURATION_ONE, START_OFFSET)
        binding.tvLogo.slideUp(ANIMATION_DURATION_TWO, START_OFFSET)
    }

    private fun splashDelay() {
        val intent = Intent(this, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
        }, SPLASH_DELAY)
    }
}
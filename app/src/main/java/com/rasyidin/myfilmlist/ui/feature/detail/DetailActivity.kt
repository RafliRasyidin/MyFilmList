package com.rasyidin.myfilmlist.ui.feature.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.rasyidin.myfilmlist.BuildConfig.BASE_URL_IMAGE
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.data.Resource
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.databinding.ActivityDetailBinding
import com.rasyidin.myfilmlist.ui.base.BaseActivity
import com.rasyidin.myfilmlist.ui.helper.loadImage
import com.rasyidin.myfilmlist.ui.helper.toYearFormat
import com.rasyidin.myfilmlist.ui.helper.toHoursAndMin
import com.rasyidin.myfilmlist.ui.helper.toSuffixCharsKMBPTE
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    private val viewModel: DetailViewModel by viewModel()
    private val args: DetailActivityArgs by navArgs()

    override fun getViewBinding() = ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar.root)
        supportActionBar?.title = null

        val movieType = args.movieType
        val tvShowType = args.tvShowType
        val isMovieType: Boolean = movieType == MOVIE_CODE
        val isTvShowType: Boolean = tvShowType == TV_SHOW_CODE

        if (isMovieType) {
            observeDetailMovie()
        } else if (isTvShowType) {
            observeDetailTvShow()
        }

        onBackClicked()

        onFavoriteClicked()
    }

    private fun observeDetailMovie() {
        viewModel.getDetailMovie(args.movieId).observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.loading.visibility = View.GONE
                    resource.data?.let {
                        showDetailMovie(it)
                    }
                }
                is Resource.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(
                        this,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun observeDetailTvShow() {
        viewModel.getDetailTvShow(args.tvShowId).observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.loading.visibility = View.GONE
                    resource.data?.let {
                        showDetailTvShow(it)
                    }
                }
                is Resource.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(
                        this,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showDetailTvShow(tvShow: TvShow) {
        binding.apply {
            imgBackdrop.loadImage(
                BASE_URL_IMAGE + tvShow.backdropPath,
                R.drawable.ic_cinema_placeholder,
                R.drawable.ic_broken_image
            )
            for (index in tvShow.genres.indices) {
                val chip = Chip(detailContainer.chipGroup.context)
                chip.text = tvShow.genres[index].name
                chip.isCheckable = false
                chip.isClickable = false
                detailContainer.chipGroup.addView(chip)
            }
            with(detailContainer) {
                tvRating.text = tvShow.voteAverage.toString()
                tvRatingCount.text = tvShow.voteCount.toSuffixCharsKMBPTE()
                tvPopular.text = tvShow.popularity.toSuffixCharsKMBPTE()
                tvReleaseDate.text = tvShow.firstAirDate?.toYearFormat()
                tvTitle.text = tvShow.name
                tvOverview.text = tvShow.overview

                imgPoster.loadImage(
                    BASE_URL_IMAGE + tvShow.posterPath,
                    R.drawable.ic_cinema_placeholder,
                    R.drawable.ic_broken_image
                )

            }
        }
    }

    private fun showDetailMovie(movie: Movie) {
        binding.apply {
            imgBackdrop.loadImage(
                BASE_URL_IMAGE + movie.backdropPath,
                R.drawable.ic_cinema_placeholder,
                R.drawable.ic_broken_image
            )
            for (index in movie.genres.indices) {
                val chip = Chip(detailContainer.chipGroup.context)
                chip.text = movie.genres[index].name
                chip.isCheckable = false
                chip.isClickable = false
                detailContainer.chipGroup.addView(chip)
            }
            with(detailContainer) {
                tvRating.text = movie.voteAverage.toString()
                tvRatingCount.text = movie.voteCount.toSuffixCharsKMBPTE()
                tvPopular.text = movie.popularity.toSuffixCharsKMBPTE()
                tvReleaseDate.text = movie.releaseDate?.toYearFormat()
                tvRuntime.text = movie.runtime.toHoursAndMin()
                tvTitle.text = movie.title
                tvOverview.text = movie.overview

                imgPoster.loadImage(
                    BASE_URL_IMAGE + movie.posterPath,
                    R.drawable.ic_cinema_placeholder,
                    R.drawable.ic_broken_image
                )
            }
        }
    }

    private fun onBackClicked() {
        binding.toolbar.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun onFavoriteClicked() {
        binding.toolbar.imgFavorite.setOnClickListener {
            val state = true
            if (state) {
                Snackbar.make(binding.root, R.string.favorited, Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, R.string.unfavorited, Snackbar.LENGTH_SHORT).show()
            }
            favoriteState(state, binding.toolbar.imgFavorite)
        }
    }

    private fun favoriteState(state: Boolean, image: ImageView) {
        image.apply {
            if (state) {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_favorite
                    )
                )
            } else {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_favorite_border
                    )
                )
            }
        }
    }

    companion object {
        const val MOVIE_ID = "movieId"
        const val MOVIE_TYPE = "movieType"
        const val MOVIE_CODE = 0

        const val TV_SHOW_ID = "tvShowId"
        const val TV_SHOW_TYPE = "tvShowType"
        const val TV_SHOW_CODE = 1
    }
}
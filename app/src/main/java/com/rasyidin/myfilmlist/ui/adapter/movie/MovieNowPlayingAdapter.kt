package com.rasyidin.myfilmlist.ui.adapter.movie

import com.rasyidin.myfilmlist.BuildConfig.BASE_URL_IMAGE
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.databinding.ItemFilmLandscapeBinding
import com.rasyidin.myfilmlist.ui.adapter.BaseAdapter
import com.rasyidin.myfilmlist.ui.helper.loadImage

class MovieNowPlayingAdapter : BaseAdapter<Movie>(R.layout.item_film_landscape) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val movie = data[position]
        val binding = ItemFilmLandscapeBinding.bind(holder.itemView)
        with(binding) {
            imgFilm.loadImage(
                BASE_URL_IMAGE + movie.backdropPath,
                R.drawable.ic_cinema_placeholder,
                R.drawable.ic_broken_image
            )
            popularContainer.tvRatingCount.text = movie.voteAverage.toString()

            root.setOnClickListener {
                onItemClickListener?.invoke(movie)
            }
        }
    }
}
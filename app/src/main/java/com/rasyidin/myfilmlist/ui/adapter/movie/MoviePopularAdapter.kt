package com.rasyidin.myfilmlist.ui.adapter.movie

import com.rasyidin.myfilmlist.BuildConfig
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.databinding.ItemFilmPotraitBinding
import com.rasyidin.myfilmlist.ui.adapter.BaseAdapter
import com.rasyidin.myfilmlist.ui.helper.loadImage

open class MoviePopularAdapter : BaseAdapter<Movie>(R.layout.item_film_potrait) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val movie = data[position]
        val binding = ItemFilmPotraitBinding.bind(holder.itemView)
        with(binding) {
            imgFilm.loadImage(
                BuildConfig.BASE_URL_IMAGE + movie.posterPath,
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
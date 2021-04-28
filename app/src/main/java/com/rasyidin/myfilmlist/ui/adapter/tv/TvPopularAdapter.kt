package com.rasyidin.myfilmlist.ui.adapter.tv

import com.rasyidin.myfilmlist.BuildConfig
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.databinding.ItemFilmPotraitBinding
import com.rasyidin.myfilmlist.ui.adapter.BaseAdapter
import com.rasyidin.myfilmlist.ui.helper.loadImage

open class TvPopularAdapter : BaseAdapter<TvShow>(R.layout.item_film_potrait) {
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val tvShow = data[position]
        val binding = ItemFilmPotraitBinding.bind(holder.itemView)
        with(binding) {
            imgFilm.loadImage(
                BuildConfig.BASE_URL_IMAGE + tvShow.posterPath,
                R.drawable.ic_cinema_placeholder,
                R.drawable.ic_broken_image
            )
            popularContainer.tvRatingCount.text = tvShow.voteAverage.toString()

            root.setOnClickListener {
                onItemClickListener?.invoke(tvShow)
            }
        }
    }
}
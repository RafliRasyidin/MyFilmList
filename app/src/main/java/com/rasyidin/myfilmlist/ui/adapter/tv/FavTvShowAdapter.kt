package com.rasyidin.myfilmlist.ui.adapter.tv

import androidx.recyclerview.widget.DiffUtil
import com.rasyidin.myfilmlist.BuildConfig.BASE_URL_IMAGE
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.domain.model.TvShow
import com.rasyidin.myfilmlist.databinding.ItemFilmPotraitBinding
import com.rasyidin.myfilmlist.ui.adapter.BasePagedListAdapter
import com.rasyidin.myfilmlist.ui.helper.loadImage

class FavTvShowAdapter(onClick: (TvShow) -> Unit) :
    BasePagedListAdapter<TvShow>(onClick, R.layout.item_film_potrait, diffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvShow = getItem(position)
        val binding = ItemFilmPotraitBinding.bind(holder.itemView)
        binding.apply {
            imgFilm.loadImage(
                BASE_URL_IMAGE + tvShow?.posterPath,
                R.drawable.ic_cinema_placeholder,
                R.drawable.ic_broken_image
            )

            popularContainer.tvRatingCount.text = tvShow?.voteAverage.toString()

            root.setOnClickListener {
                onItemClick(tvShow)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }
}
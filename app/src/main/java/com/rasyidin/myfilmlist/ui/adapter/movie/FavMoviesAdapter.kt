package com.rasyidin.myfilmlist.ui.adapter.movie

import androidx.recyclerview.widget.DiffUtil
import com.rasyidin.myfilmlist.BuildConfig.BASE_URL_IMAGE
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.domain.model.Movie
import com.rasyidin.myfilmlist.databinding.ItemFilmPotraitBinding
import com.rasyidin.myfilmlist.ui.adapter.BasePagedListAdapter
import com.rasyidin.myfilmlist.ui.helper.loadImage

class FavMoviesAdapter(onClick: (Movie) -> Unit) :
    BasePagedListAdapter<Movie>(onClick, diffCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        val binding = ItemFilmPotraitBinding.bind(holder.itemView)
        binding.apply {
            imgFilm.loadImage(
                BASE_URL_IMAGE + movie?.posterPath,
                R.drawable.ic_cinema_placeholder,
                R.drawable.ic_broken_image
            )
            popularContainer.tvRatingCount.text = movie?.voteAverage.toString()

            root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}
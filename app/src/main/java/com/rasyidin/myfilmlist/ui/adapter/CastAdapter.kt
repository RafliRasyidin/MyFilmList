package com.rasyidin.myfilmlist.ui.adapter

import com.rasyidin.myfilmlist.BuildConfig.BASE_URL_IMAGE
import com.rasyidin.myfilmlist.R
import com.rasyidin.myfilmlist.core.domain.model.Person
import com.rasyidin.myfilmlist.databinding.ItemCastBinding
import com.rasyidin.myfilmlist.ui.helper.loadImage

class CastAdapter : BaseAdapter<Person>(R.layout.item_cast) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val person = data[position]
        val binding = ItemCastBinding.bind(holder.itemView)
        with(binding) {
            imgCast.loadImage(
                BASE_URL_IMAGE + person.profilePath,
                R.drawable.ic_star_outline,
                R.drawable.ic_broken_image
            )
            tvName.text = person.name
            tvCharacter.text = person.character

            root.setOnClickListener {
                onItemClickListener?.invoke(person)
            }
        }
    }
}
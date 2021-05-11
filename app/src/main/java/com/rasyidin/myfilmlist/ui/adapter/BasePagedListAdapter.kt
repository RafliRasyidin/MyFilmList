package com.rasyidin.myfilmlist.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rasyidin.myfilmlist.R

abstract class BasePagedListAdapter<T>(
    val onClick: ((T) -> Unit)?,
    diffUtil: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, BasePagedListAdapter<T>.ViewHolder>(diffUtil) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    protected fun onItemClick(item: T?) = onClick?.let {
        if (item != null) {
            it(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_film_potrait, parent, false)
        )
    }

}
package com.mayank.kotlinmvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mayank.kotlinmvvm.databinding.AdapterPhotoBinding
import com.mayank.kotlinmvvm.model.Photo
import com.mayank.kotlinmvvm.utils.Constants.CUSTOM_PHOTO_URL

class PhotoListAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var photos = mutableListOf<Photo>()

    fun setPhotoList(photo: List<Photo>) {
        this.photos = photo.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterPhotoBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val photo = photos[position]
        holder.binding.name.text = photo.title
        Glide.with(holder.itemView.context).load(getCustomPhotoUrl(photo.id, 300, 150))
            .into(holder.binding.imageview)

    }

    override fun getItemCount(): Int {
        return photos.size
    }

    private fun getCustomPhotoUrl(photoId: Int, width: Int, height: Int): String {
        return CUSTOM_PHOTO_URL.plus("$photoId/$width/$height")
    }
}

class MainViewHolder(val binding: AdapterPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

}
package com.satya.movee.ui.adapter.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satya.movee.R
import com.satya.movee.constants.Constant
import com.satya.movee.databinding.MovieRvLayoutBinding
import com.satya.movee.databinding.PopularrvlayoutBinding
import com.satya.movee.ui.adapter.TvViewHolder

class PopularPersonAdapter : RecyclerView.Adapter<PopularPersonAdapter.PopularViewHolder>() {

    private var popularPersons = mutableListOf<com.satya.movee.model.popular.Result?>()

    fun setPopularPeople(movies: List<com.satya.movee.model.popular.Result?>?) {
        this.popularPersons = movies!!.toMutableList()
        notifyDataSetChanged()
    }

    class PopularViewHolder(val binding: PopularrvlayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PopularrvlayoutBinding.inflate(inflater,parent,false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val movie = popularPersons[position]

        Glide.with(holder.itemView.context)
            .load(Constant.imageBaseUrl +movie!!.profilePath)
            .placeholder(R.drawable.logo)
            .into(holder.binding.personImage)

        holder.binding.personName.text = movie.name

//        holder.id = movies[position]?.id!!
//        val bundle = Bundle()
//        bundle.putInt("id",holder.id)
//
//        holder.itemView.setOnClickListener {
//            val nav = holder.binding.root.findNavController()
//            nav.navigate(R.id.navigation_tv_series_detail,bundle)
//        }
    }

    override fun getItemCount(): Int {
        return popularPersons.size
    }

}
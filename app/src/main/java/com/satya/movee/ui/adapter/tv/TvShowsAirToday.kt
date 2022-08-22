package com.satya.movee.ui.adapter.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satya.movee.R
import com.satya.movee.constants.Constant
import com.satya.movee.databinding.MovieRvLayoutBinding
import com.satya.movee.ui.adapter.TvViewHolder

class TvShowsAirToday : RecyclerView.Adapter<TvViewHolder>() {

    private var movies = mutableListOf<com.satya.movee.model.trendingTvShows.Result?>()

    fun setLatestAirShows(movies: List<com.satya.movee.model.trendingTvShows.Result?>?) {
        this.movies = movies!!.toMutableList()
        notifyDataSetChanged()
    }

    // class ViewHolder(val binding: MovieRvLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieRvLayoutBinding.inflate(inflater,parent,false)
        return TvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val movie = movies[position]

        Glide.with(holder.itemView.context)
            .load(Constant.imageBaseUrl +movie!!.posterPath)
            .placeholder(R.drawable.logo)
            .into(holder.binding.trendingMovie)

        holder.id = movies[position]?.id!!
        val bundle = Bundle()
        bundle.putInt("id",holder.id)

        holder.itemView.setOnClickListener {
            val nav = holder.binding.root.findNavController()
            nav.navigate(R.id.navigation_tv_series_detail,bundle)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}
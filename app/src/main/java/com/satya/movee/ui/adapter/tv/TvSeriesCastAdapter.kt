package com.satya.movee.ui.adapter.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satya.movee.R
import com.satya.movee.constants.Constant
import com.satya.movee.databinding.CastRvLayoutBinding
import com.satya.movee.model.trendingMovies.Cast
import com.satya.movee.ui.adapter.movies.MovieCreditsAdapter

class TvSeriesCastAdapter: RecyclerView.Adapter<TvSeriesCastAdapter.TvViewHolder>() {

    private var movies = mutableListOf<com.satya.movee.model.trendingTvShows.Cast?>()

    fun getTvSeriesCredits(movies: List<com.satya.movee.model.trendingTvShows.Cast?>?) {
        this.movies = movies!!.toMutableList()
        notifyDataSetChanged()
    }

    class TvViewHolder(val binding: CastRvLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CastRvLayoutBinding.inflate(inflater,parent,false)
        return TvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val movie = movies[position]
        Glide.with(holder.itemView.context)
            .load(Constant.imageBaseUrl +movie!!.profilePath)
            .placeholder(R.drawable.logo)
            .into(holder.binding.actorImage)
        holder.binding.actorName.text = movie.name

//        holder.id = movies[position]?.id!!
//        val bundle = Bundle()
//        bundle.putInt("id",holder.id)
//
//        holder.itemView.setOnClickListener {
//            val nav = holder.binding.root.findNavController()
//            nav.navigate(R.id.navigation_detail,bundle)
//        }
    }

    override fun getItemCount(): Int {
        if(movies.size <= 10) {
            return movies.size
        } else {
            return movies.size / 2
        }
    }

}
package com.satya.movee.ui.adapter.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satya.movee.R
import com.satya.movee.constants.Constant
import com.satya.movee.databinding.SeasorvlayoutBinding

class TvSeasonsAdapter : RecyclerView.Adapter<TvSeasonsAdapter.TvViewHolder>() {

    private var tvs = mutableListOf<com.satya.movee.model.trendingTvShows.Season?>()

    fun setTvShowsSeason(movies: List<com.satya.movee.model.trendingTvShows.Season?>?) {
        this.tvs = movies!!.toMutableList()
        notifyDataSetChanged()
    }

     class TvViewHolder(val binding: SeasorvlayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SeasorvlayoutBinding.inflate(inflater,parent,false)
        return TvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tv = tvs[position]

        Glide.with(holder.itemView.context)
            .load(Constant.imageBaseUrl +tv!!.posterPath)
            .placeholder(R.drawable.logo)
            .into(holder.binding.tvSesaonImage)

        holder.binding.seasonTitle.text = tv.name
        holder.binding.overview.text = tv.overview
        ("No. of Seasons: "+tv.seasonNumber.toString()).also { holder.binding.seasonCount.text = it }
        ("No. of episodes: "+tv.episodeCount.toString()).also { holder.binding.numberOfEpisodes.text = it }
        if(tv.airDate == "") {
            "First Aired on : N / A".also { holder.binding.firstAiredOn.text = it }
        } else {
            ("First Aired on: "+tv.airDate).also { holder.binding.firstAiredOn.text = it }
        }


    }

    override fun getItemCount(): Int {
        return tvs.size
    }

}
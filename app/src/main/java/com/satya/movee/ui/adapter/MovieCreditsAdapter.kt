package com.satya.movee.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satya.movee.R
import com.satya.movee.constants.Constant
import com.satya.movee.databinding.CastRvLayoutBinding
import com.satya.movee.model.trendingMovies.Cast

class MovieCreditsAdapter: RecyclerView.Adapter<MovieCreditsAdapter.ViewHolder>() {

    private var movies = mutableListOf<Cast?>()

    fun getMovieCredits(movies: List<Cast?>?) {
        this.movies = movies!!.toMutableList()
        notifyDataSetChanged()
    }

     class ViewHolder(val binding: CastRvLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CastRvLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
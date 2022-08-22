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
import com.satya.movee.databinding.UpcomingrvlayoutBinding
import com.satya.movee.model.trendingMovies.Result
import com.satya.movee.ui.adapter.ViewHolder

class UpCominGMoviesAdapter: RecyclerView.Adapter<UpCominGMoviesAdapter.ViewHolder>() {

    private var movies = mutableListOf<Result?>()

    fun setMovieList(movies: List<Result?>?) {
        this.movies = movies!!.toMutableList()
        notifyDataSetChanged()
    }

     class ViewHolder(val binding: UpcomingrvlayoutBinding) : RecyclerView.ViewHolder(binding.root) {
         var id: Int = 0
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UpcomingrvlayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
            nav.navigate(R.id.navigation_detail,bundle)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}
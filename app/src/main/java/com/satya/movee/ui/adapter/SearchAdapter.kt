package com.satya.movee.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satya.movee.R
import com.satya.movee.constants.Constant
import com.satya.movee.databinding.MovieRvSearchBinding


class SearchAdapter: RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var movies = mutableListOf<com.satya.movee.model.search.movie.Result?>()

    fun setSearchData(movies: List<com.satya.movee.model.search.movie.Result?>?) {
        this.movies = movies!!.toMutableList()
        this.notifyDataSetChanged()

    }
        class ViewHolder(val binding: MovieRvSearchBinding) : RecyclerView.ViewHolder(binding.root) {
            var id: Int = 0
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieRvSearchBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movie = movies[position]

        Glide.with(holder.itemView.context)
            .load(Constant.imageBaseUrl + movie?.posterPath)
            .placeholder(R.drawable.logo)
            .into(holder.binding.moviePoster)

        holder.binding.movieName.text = movie?.title
        "${movie?.voteAverage} / 10".also { holder.binding.rating.text = it }

        holder.id = movies[position]?.id!!
        val bundle = Bundle()
        bundle.putInt("id",holder.id)

        holder.itemView.setOnClickListener {
            val nav = holder.binding.root.findNavController()
            nav.navigate(R.id.navigation_detail,bundle)
        }

    }

    override fun getItemCount(): Int {
        if(movies.size <= 10) {
            return movies.size
        } else {
            return movies.size / 2
        }
    }

}
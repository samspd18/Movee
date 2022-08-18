package com.satya.movee.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.satya.movee.R
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.constants.Constant
import com.satya.movee.constants.Constant.Companion.youtubeVideoUrl
import com.satya.movee.databinding.FragmentMovieDetailBinding
import com.satya.movee.network.RetrofitInstance
import com.satya.movee.ui.adapter.MovieAdapter
import com.satya.movee.ui.adapter.MovieCreditsAdapter
import com.satya.movee.viewmodel.ViewModel.MoviesViewModel
import com.satya.movee.viewmodel.ViewModelFactory.ViewModelFactory


class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesViewModel
    private val retrofitService = RetrofitInstance.getInstance()
    private var movie_id: Int = 0
    private var adapter = MovieCreditsAdapter()
    private var similarMoviesAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelFactory(MoviesRepositories(retrofitService)))[MoviesViewModel::class.java]

        movie_id = arguments?.getInt("id")!!

        getDetailMovie()
        getMovieCredits()
        getSimilarMovies()
        loadLoader()

        viewModel.getMovieDetail(movie_id)
        viewModel.getMovieYoutubeVideo(movie_id)
        viewModel.getMovieCredits(movie_id)
        viewModel.getSimilarMovies(movie_id)

        playVideo()
        backButton()

        binding.download.setOnClickListener {
            Toast.makeText(context, "Video will be downloaded soon", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun loadLoader() {
        viewModel.isLoading.observe(viewLifecycleOwner
        ) { isLoading ->

            if (isLoading != null) {
                if (isLoading) {
                    // hide your progress bar
                    binding.progressBarDetailPage.visibility = View.GONE
                    binding.innerLayout.visibility = View.VISIBLE
                } else if(!isLoading) {
                    // binding.innerLayout.visibility = View.GONE
                    binding.progressBarDetailPage.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getDetailMovie() {
        viewModel.getMovieDetail.observe(viewLifecycleOwner) {

            Glide.with(this)
                .load(Constant.imageBaseUrl +it.backdropPath)
                .placeholder(R.drawable.logo)
                .into(binding.detailImage)
            "Release Date: ${it.releaseDate}".also { binding.releaseDate.text = it }
            binding.title.text = it.title
            binding.detailText.text = it.overview
            "${it.runtime} min".also { binding.duration.text = it }

            val rating = String.format("%.1f", it.voteAverage).toDouble() / 2
            if(rating == 0.0) {
                "Rating: N/A".also { binding.rating.text = it }
            } else {
                "Rating: ${rating} / 5".also { binding.rating.text = it }
            }
            binding.tagline.text = it.tagline

            val genresSize = it.genres?.size!!
            if (genresSize > 2) {
                "${it.genres[0]!!.name} | ${it.genres[1]!!.name} | ${it.genres[2]!!.name}".also { binding.geners.text = it }
            } else if(genresSize > 1) {
                "${it.genres[0]!!.name} | ${it.genres[1]!!.name}".also { binding.geners.text = it }
            } else if(genresSize > 0) {
                "${it.genres[0]!!.name} ".also { binding.geners.text = it }
            }
        }
        errorMessage()
    }

    private fun playVideo() {
        viewModel.getMovieVideo.observe(viewLifecycleOwner) {
            var videoKey = ""
            if(it.results?.size != 0) {
                for(item in it?.results!!) {
                    if(item.type == "Trailer" ) {
                        videoKey = item.key.toString()
                        break
                    }
                }
            }

            binding.playInApp.setOnClickListener {
                if(videoKey == "") {
                    Toast.makeText(requireContext(), "Sorry , there are no video till now.", Toast.LENGTH_SHORT).show()
                } else {
                    val bundle = Bundle()
                    bundle.putString("video_key", youtubeVideoUrl+ videoKey)

                    val nav = findNavController()
                    nav.navigate(R.id.navigation_youtube_player,bundle)
                }
            }

            binding.playTralier.setOnClickListener {

                if(videoKey == "") {
                    Toast.makeText(requireContext(), "Sorry , there are no video till now.", Toast.LENGTH_SHORT).show()
                } else {
                    val intent  =  Intent(Intent.ACTION_VIEW, Uri.parse(youtubeVideoUrl+videoKey));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.setPackage("com.google.android.youtube")
                    startActivity(intent)
                }
            }

            binding.share.setOnClickListener {
                if(videoKey == "") {
                    Toast.makeText(requireContext(), "Sorry , there are no video till now.", Toast.LENGTH_SHORT).show()
                } else {
                    val shareIntent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        this.putExtra(Intent.EXTRA_TEXT, youtubeVideoUrl+videoKey)
                        this.type = "text/plain"
                    }
                    startActivity(shareIntent)
                }
            }
        }
    }

    private fun getMovieCredits() {
        binding.castRecyclerView.adapter = adapter
        viewModel.getMovieCredits.observe(viewLifecycleOwner) {
            adapter.getMovieCredits(it.cast)
        }

        errorMessage()
    }

    private fun getSimilarMovies() {
        binding.similarMovieRecyclerView.adapter = similarMoviesAdapter
        viewModel.getSimilarMovies.observe(viewLifecycleOwner) {
            similarMoviesAdapter.setMovieList(it.results)
        }

        errorMessage()
    }
    private fun errorMessage() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    private fun backButton() {
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
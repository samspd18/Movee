package com.satya.movee.ui.fragment.tv

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
import com.satya.movee.databinding.FragmentSeriesDetailBinding
import com.satya.movee.databinding.SeasorvlayoutBinding
import com.satya.movee.network.RetrofitInstance
import com.satya.movee.ui.adapter.tv.TvSeasonsAdapter
import com.satya.movee.ui.adapter.tv.TvSeriesCastAdapter
import com.satya.movee.viewmodel.ViewModel.TvShowsViewModel
import com.satya.movee.viewmodel.ViewModelFactory.TvViewModelFactory


class SeriesDetailFragment : Fragment() {

    private var _binding: FragmentSeriesDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TvShowsViewModel
    private val retrofitService = RetrofitInstance.getInstance()
    private var tv_id: Int = 0
    private var adapter = TvSeasonsAdapter()
    private var tvSeriesCast = TvSeriesCastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSeriesDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, TvViewModelFactory(MoviesRepositories(retrofitService)))[TvShowsViewModel::class.java]
        tv_id = arguments?.getInt("id")!!

        viewModel.getTvSeriesDetail(tv_id)
        viewModel.getTvSeriesCast(tv_id)
        viewModel.getSeriesVideo(tv_id)

        loadLoader()
        backButton()

        getTvSeriesDetails()
        getTvSeriesTralier()
        getTvSeriesCast()

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

    private fun getTvSeriesDetails() {
        //for image binding
        viewModel.getTvSeriesDetail.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(Constant.imageBaseUrl +it.backdropPath)
                .placeholder(R.drawable.logo)
                .into(binding.detailImage)

            binding.title.text = it.name
            binding.tagline.text = it.tagline
            binding.detailText.text = it.overview

            //for rating
            val rating = String.format("%.1f", it.voteAverage).toDouble() / 2
            if(it.voteAverage != 0.0) {
                "Rating: $rating / 5".also { binding.rating.text = it }
            } else {
                "Rating: N/A".also { binding.rating.text = it }
            }
            "First Air on: ${it.firstAirDate}".also { binding.releaseDate.text = it }
            "Total Seasons : ${it.numberOfSeasons}".also { binding.duration.text = it }

            //for genres
            if(it.genres!!.size > 2) {
                "${it.genres[0]!!.name} | ${it.genres[1]!!.name} | ${it.genres[2]!!.name}".also { binding.geners.text = it }
            } else if(it.genres.size > 1) {
                "${it.genres[0]!!.name} | ${it.genres[1]!!.name} ".also { binding.geners.text = it }
            } else if(it.genres.isNotEmpty()) {
                "${it.genres[0]!!.name} ".also { binding.geners.text = it }
            }

            //for cast
            if(it.seasons!!.isNotEmpty()) {
                binding.seasonRecyclerView.adapter = adapter
                adapter.setTvShowsSeason(it.seasons)
            } else {
                binding.seasons.visibility = View.GONE
                binding.seasonRecyclerView.visibility = View.GONE
            }

        }
    }

    private fun getTvSeriesCast() {
        binding.castRecyclerView.adapter = tvSeriesCast
        viewModel.getTvSeriesCast.observe(viewLifecycleOwner) {
            tvSeriesCast.getTvSeriesCredits(it.cast)
        }
    }

    private fun getTvSeriesTralier() {
        var videoKey = ""
        viewModel.getTvSeriesVide.observe(viewLifecycleOwner) {

            if(it.results?.size != 0) {
                for(item in it?.results!!) {
                    if(item!!.type == "Trailer" ) {
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
                    bundle.putString("video_key", Constant.youtubeVideoUrl + videoKey)

                    val nav = findNavController()
                    nav.navigate(R.id.navigation_youtube_player,bundle)
                }
            }

            binding.playTralier.setOnClickListener {

                if(videoKey == "") {
                    Toast.makeText(requireContext(), "Sorry , there are no video till now.", Toast.LENGTH_SHORT).show()
                } else {
                    val intent  =  Intent(Intent.ACTION_VIEW, Uri.parse(Constant.youtubeVideoUrl +videoKey));
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
                        this.putExtra(Intent.EXTRA_TEXT, Constant.youtubeVideoUrl +videoKey)
                        this.type = "text/plain"
                    }
                    startActivity(shareIntent)
                }
            }
        }
    }
    private fun backButton() {
        binding.backTv.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
package com.satya.movee.ui.fragment.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.satya.movee.R
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.constants.Constant
import com.satya.movee.databinding.FragmentSeriesDetailBinding
import com.satya.movee.network.RetrofitInstance
import com.satya.movee.viewmodel.ViewModel.TvShowsViewModel
import com.satya.movee.viewmodel.ViewModelFactory.TvViewModelFactory


class SeriesDetailFragment : Fragment() {

    private var _binding: FragmentSeriesDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TvShowsViewModel
    private val retrofitService = RetrofitInstance.getInstance()
    private var tv_id: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSeriesDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, TvViewModelFactory(MoviesRepositories(retrofitService)))[TvShowsViewModel::class.java]
        tv_id = arguments?.getInt("id")!!

        viewModel.getTvSeriesDetail(tv_id)

        loadLoader()
        getTvSeriesDetails()
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
        viewModel.getTvSeriesDetail.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(Constant.imageBaseUrl +it.backdropPath)
                .placeholder(R.drawable.logo)
                .into(binding.detailImage)

            binding.title.text = it.name
            binding.tagline.text = it.tagline
            binding.detailText.text = it.overview

            val rating = String.format("%.1f", it.voteAverage).toDouble() / 2
            if(it.voteAverage != 0.0) {
                "Rating: rating / 5".also { binding.rating.text = it }
            } else {
                "Rating: N/A".also { binding.rating.text = it }
            }

        }
    }
}
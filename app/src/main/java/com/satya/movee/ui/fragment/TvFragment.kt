package com.satya.movee.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.satya.movee.R
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.constants.Constant
import com.satya.movee.databinding.FragmentTvBinding
import com.satya.movee.network.RetrofitInstance
import com.satya.movee.ui.adapter.MovieAdapter
import com.satya.movee.ui.adapter.tv.TrendingTvShowsAdapter
import com.satya.movee.viewmodel.ViewModel.MoviesViewModel
import com.satya.movee.viewmodel.ViewModel.TvShowsViewModel
import com.satya.movee.viewmodel.ViewModelFactory.MoviesViewModelFactory
import com.satya.movee.viewmodel.ViewModelFactory.TvViewModelFactory
import kotlin.random.Random

class TvFragment : Fragment() {

    private var _binding: FragmentTvBinding? = null
    private lateinit var viewModel: TvShowsViewModel
    private val retrofitService = RetrofitInstance.getInstance()
    private lateinit var navBar: BottomNavigationView
    private val binding get() = _binding!!

    private val adapter = TrendingTvShowsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(this, TvViewModelFactory(MoviesRepositories(retrofitService)))[TvShowsViewModel::class.java]

        getAllTrendingTvShows()

        viewModel.getAllTrendingTvShows()

        loadLoader()
        return root
    }

    private fun loadLoader() {
        viewModel.isLoading.observe(viewLifecycleOwner
        ) { isLoading ->

            if (isLoading != null) {
                if (isLoading) {
                    // hide your progress bar
                    binding.loaderTv.visibility = View.GONE
                    binding.innerLayout.visibility = View.VISIBLE
                } else if(!isLoading) {
                    // binding.innerLayout.visibility = View.GONE
                    binding.loaderTv.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getAllTrendingTvShows() {
        binding.trendingRecyclerView.adapter = adapter
        viewModel.trendingMovieList.observe(viewLifecycleOwner) {
            val index = Random.nextInt(0,it?.results!!.size - 1)
            Glide.with(this)
                .load(Constant.imageBaseUrl + it.results[index]?.posterPath)
                .placeholder(R.drawable.black_background)
                .into(binding.MainPosterTv)
            "Trending in #${index+1}".also { binding.trendingPositionTv.text = it }
            val id = it.results[index]?.id!!

            binding.infoTv.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id",id)
                val nav = binding.root.findNavController()
                nav.navigate(R.id.navigation_detail,bundle)
            }
            val rating = String.format("%.1f", it.results[index]?.voteAverage).toDouble() / 2
            "Rating: $rating / 5".also { binding.ratingTv.text = it }
            adapter.setMovieList(it.results)
        }

    }

    override fun onResume() {
        super.onResume()
        navBar= requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.VISIBLE
    }
    override fun onPause() {
        super.onPause()
        navBar= requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
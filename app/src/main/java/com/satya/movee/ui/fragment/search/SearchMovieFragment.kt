package com.satya.movee.ui.fragment.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.databinding.FragmentSearchMovieBinding
import com.satya.movee.network.RetrofitInstance
import com.satya.movee.ui.adapter.SearchAdapter
import com.satya.movee.ui.adapter.tv.TvShowsSearchAdapter
import com.satya.movee.viewmodel.ViewModel.MoviesViewModel
import com.satya.movee.viewmodel.ViewModel.TvShowsViewModel
import com.satya.movee.viewmodel.ViewModelFactory.MoviesViewModelFactory
import com.satya.movee.viewmodel.ViewModelFactory.TvViewModelFactory

class SearchMovieFragment : Fragment() {

    private var _binding: FragmentSearchMovieBinding? = null
    private lateinit var viewModel: MoviesViewModel
    private lateinit var tvViewModel: TvShowsViewModel
    private val retrofitService = RetrofitInstance.getInstance()

    private val binding get() = _binding!!
    private var adapter = SearchAdapter()
    private var searchTvAdapter = TvShowsSearchAdapter()
    private var mediaType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_search, container, false)
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, MoviesViewModelFactory(MoviesRepositories(retrofitService)))[MoviesViewModel::class.java]
        tvViewModel = ViewModelProvider(this, TvViewModelFactory(MoviesRepositories(retrofitService)))[TvShowsViewModel::class.java]

        mediaType = arguments?.getString("media-type")!!

        if(mediaType == "movie") {
            binding.search.hint = "Search Movies"
            getAllSearchData()
        } else if(mediaType == "tv") {
            binding.search.hint = "Search tv / series"
            getTvSearchData()
        }



        binding.search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(searchData: Editable) {
                if(searchData.isNotEmpty()) {
                    binding.searchResultRecyclerView.visibility = View.VISIBLE
                    binding.searchResultOf.visibility = View.VISIBLE

                    "Search result of $searchData".also { binding.searchResultOf.text = it }
                    if(mediaType == "movie") {
                        viewModel.getSearchResult(searchData.toString())
                    } else if(mediaType == "tv") {
                        tvViewModel.getSearchResult(searchData.toString())
                    }
                } else {
                    binding.searchResultRecyclerView.visibility = View.GONE
                    binding.searchResultOf.visibility = View.GONE
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        backButton()

        return binding.root
    }

    private fun backButton() {
        binding.searchBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun getAllSearchData() {
        binding.searchResultRecyclerView.adapter = adapter
        viewModel.getSearchResult.observe(viewLifecycleOwner) {
            adapter.setSearchData(it.results!!)
        }

        errorMessage()
    }

    private fun getTvSearchData() {
        binding.searchResultRecyclerView.adapter = searchTvAdapter
        tvViewModel.getSearchResultTv.observe(viewLifecycleOwner) {
            searchTvAdapter.setSearchData(it.results)
        }

        errorMessage()
    }

    private fun errorMessage() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

}
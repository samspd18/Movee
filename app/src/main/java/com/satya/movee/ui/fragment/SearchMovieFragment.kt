package com.satya.movee.ui.fragment

import android.os.Bundle
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
import com.satya.movee.viewmodel.ViewModel.MoviesViewModel
import com.satya.movee.viewmodel.ViewModelFactory.MoviesViewModelFactory

class SearchMovieFragment : Fragment() {

    private var _binding: FragmentSearchMovieBinding? = null
    private lateinit var viewModel: MoviesViewModel
    private val retrofitService = RetrofitInstance.getInstance()

    private val binding get() = _binding!!
    private var adapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_search, container, false)
        _binding = FragmentSearchMovieBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, MoviesViewModelFactory(MoviesRepositories(retrofitService)))[MoviesViewModel::class.java]

        getAllSearchData()

        binding.searchButton.setOnClickListener {
            val searchData: String = binding.search.text.toString()

            if(searchData.isEmpty()) {
                Toast.makeText(context, "Search bar can't is empty", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getSearchResult(searchData)
                "Search result of $searchData".also { binding.searchResultOf.text = it }
            }

        }
        return binding.root
    }

    private fun getAllSearchData() {
        binding.searchResultRecyclerView.adapter = adapter
        viewModel.getSearchResult.observe(viewLifecycleOwner) {
            adapter.setSearchData(it.results)
        }

        errorMessage()
    }

    private fun errorMessage() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

}
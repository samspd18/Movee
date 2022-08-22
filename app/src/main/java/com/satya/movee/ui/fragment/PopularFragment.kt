package com.satya.movee.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.satya.movee.R
import com.satya.movee.Repositories.MoviesRepositories
import com.satya.movee.databinding.FragmentPopularBinding
import com.satya.movee.network.RetrofitInstance
import com.satya.movee.viewmodel.ViewModel.MoviesViewModel
import com.satya.movee.viewmodel.ViewModel.PopularViewModel
import com.satya.movee.viewmodel.ViewModel.TvShowsViewModel
import com.satya.movee.viewmodel.ViewModelFactory.MoviesViewModelFactory
import com.satya.movee.viewmodel.ViewModelFactory.PopularViewModelFacory
//import com.satya.movee.viewmodel.ViewModelFactory.PopularViewModelFacory
import com.satya.movee.viewmodel.ViewModelFactory.TvViewModelFactory

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding?= null
    private lateinit var viewModel: PopularViewModel
    private val retrofitService = RetrofitInstance.getInstance()
    private val binding get() = _binding!!
    private lateinit var navBar: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val notificationsViewModel =
//            ViewModelProvider(this)[MoviesViewModel::class.java]

        _binding = FragmentPopularBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this, PopularViewModelFacory(MoviesRepositories(retrofitService)))[PopularViewModel::class.java]



        return binding.root
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
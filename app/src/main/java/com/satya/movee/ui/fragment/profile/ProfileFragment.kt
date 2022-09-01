package com.satya.movee.ui.fragment.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.satya.movee.R
import com.satya.movee.constants.Constant.Companion.sharedPrefFile
import com.satya.movee.databinding.FragmentProfileBinding
import com.satya.movee.ui.activity.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name","")
        Log.e("profile", name.toString() )
        val image = sharedPreferences.getString("profileUrl","")
        val email = sharedPreferences.getString("email","")

        if(name == "") {
            binding.profileName.text = "N/A"
        }else {
            binding.profileName.text = name
        }
        if(image != "") {
            Glide.with(this)
                .load(image)
                .placeholder(R.drawable.man)
                .into(binding.profileImage)
        }

        if(email == "") {
            binding.profileEmail.text = "N/A"
        }else {
            binding.profileEmail.text = email
        }
        back()
        logout()

        return binding.root
    }

    private fun back() {
        binding.profileBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
    private fun logout(){
        binding.logOut.setOnClickListener {

            val preferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}
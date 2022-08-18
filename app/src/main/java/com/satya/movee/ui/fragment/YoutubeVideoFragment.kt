package com.satya.movee.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.satya.movee.R
import com.satya.movee.databinding.FragmentMovieDetailBinding
import com.satya.movee.databinding.FragmentYoutubeVideoBinding

class YoutubeVideoFragment : Fragment() {

    private var _binding: FragmentYoutubeVideoBinding? = null
    private val binding get() = _binding!!
    private var videoLink: String = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYoutubeVideoBinding.inflate(inflater, container, false)

        videoLink = arguments?.getString("video_key").toString()

        Log.e("videoLink", videoLink )

        val webSettings = binding.youtubeWebView.settings
        webSettings.javaScriptEnabled = true

        binding.youtubeWebView.webViewClient = WebViewClient()
        binding.youtubeWebView.webChromeClient = WebChromeClient()
        binding.youtubeWebView.loadUrl(videoLink)

        binding.youtubeWebView.webViewClient = object:WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.daylantern.newssphere.views.activities

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.daylantern.newssphere.Constant
import com.daylantern.newssphere.R
import com.daylantern.newssphere.databinding.ActivityNewsDetailBinding

class NewsDetail : AppCompatActivity() {
    
    private lateinit var binding: ActivityNewsDetailBinding
    
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.webView.apply {
            val url = intent.getStringExtra(Constant.INTENT_URL)
            if(!url.isNullOrEmpty()){
                loadUrl(url)
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        binding.progressBar.visibility = View.VISIBLE
                    }
        
                    override fun onPageCommitVisible(view: WebView?, url: String?) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
                settings.javaScriptEnabled = true
            }
        }
    }
}
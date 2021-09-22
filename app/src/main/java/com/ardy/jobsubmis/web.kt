package com.ardy.jobsubmis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.ardy.jobsubmis.connect.NewsDataApi

import com.ardy.jobsubmis.databinding.ActivityWebBinding

class web : AppCompatActivity() {
    private lateinit var binding: ActivityWebBinding
    companion object{
        const val ARG_section_username= "username"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.allowContentAccess = true
        if ( intent?.getParcelableExtra(ARG_section_username) as? NewsDataApi != null ) {
            val user = intent.getParcelableExtra(ARG_section_username) as? NewsDataApi
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.settings.allowContentAccess = true
            supportActionBar?.title = user?.namenews.toString()
            binding.webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    view.loadUrl("javascript:alert('Web berhasil dimuat')")
                }
            }

            binding.webView.webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(view: WebView, url: String, message: String, result: android.webkit.JsResult): Boolean {
                    Toast.makeText(this@web, message, Toast.LENGTH_LONG).show()
                    result.confirm()
                    return true
                }
            }

            binding.webView.loadUrl(user?.url.toString())

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
       finish()
    }
}
package com.example.ilw_pocky

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.ilw_pocky.databinding.ActivityAlatPanenBinding

class AlatPanenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlatPanenBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlatPanenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_alat_panen)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        //link web
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://www.ptinsansumbermandiri.com")

        // Agar Toolbar hide show saat scroll web
        binding.webView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.appBarLayout.setExpanded(false, true) // Sembunyikan AppBar
            } else if (scrollY < oldScrollY) {
                binding.appBarLayout.setExpanded(true, true)  // Tampilkan AppBar
            }
        }
    }

    // Navigasi tombol back pada Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // back
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack() // Kembali ke halaman web sebelumnya
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed() // Keluar dari activity
        }
    }
}
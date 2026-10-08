package com.example.ilw_pocky

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ilw_pocky.databinding.ActivitySawitBinding

class SawitActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySawitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySawitBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_sawit)
            subtitle = getString(R.string.tagline)
        }

        // Tombol "Mulai Sekarang" pindah ke RegisActivity
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)
        }

        // Tombol web pindah ke AlatPanenActivity (WebView)
        binding.btnWeb.setOnClickListener {
            val intent = Intent(this, AlatPanenActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "Cari diklik", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                Toast.makeText(this, "Pengaturan diklik", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
package com.example.ilw_pocky

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ilw_pocky.Home.HomeFragment
import com.example.ilw_pocky.Laporan.LaporanFragment
import com.example.ilw_pocky.manajemen_pengguna.ManajemenPenggunaFragment
import com.example.ilw_pocky.databinding.ActivityBaseBinding
import com.example.ilw_pocky.kelola_barang.KelolaBarangFragment

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.kelola_barang -> {
                    replaceFragment(KelolaBarangFragment())
                    true
                }
                R.id.laporan -> {
                    replaceFragment(LaporanFragment())
                    true
                }
                R.id.manajemen_pengguna -> {
                    replaceFragment(ManajemenPenggunaFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}
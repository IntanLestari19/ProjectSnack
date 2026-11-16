package com.example.ilw_pocky.Home.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ilw_pocky.databinding.ActivityInfoPalmventoryBinding

class InfoPalmventoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoPalmventoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoPalmventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentsList = listOf(
            InfoSlide1Fragment(),
            InfoSlide2Fragment(),
            InfoSlide3Fragment()
        )
        val adapter = InfoFragmentAdapter(this, fragmentsList)
        binding.infoViewPager.adapter = adapter

        binding.dotIndicator.attachTo(binding.infoViewPager)
    }
}
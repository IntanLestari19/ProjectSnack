package com.example.ilw_pocky.kelola_barang

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class KelolaBarangTabsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabBarangFragment.newInstance("SEMUA")
            1 -> TabBarangFragment.newInstance("PUPUK")
            2 -> TabBarangFragment.newInstance("ALAT KERJA")
            else -> throw IllegalStateException("Posisi tab tidak valid")
        }
    }
}
package com.example.ilw_pocky.kelola_barang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.ilw_pocky.R
import com.example.ilw_pocky.databinding.FragmentKelolaBarangBinding
import com.google.android.material.tabs.TabLayoutMediator

class KelolaBarangFragment : Fragment() {

    private var _binding: FragmentKelolaBarangBinding? = null
    private val binding get() = _binding!!

    // Data statis disesuaikan dengan tampilan website Palmventory
    private val daftarBarang = listOf(
        BarangModel(
            sku = "BRG04",
            nama = "Pupuk NPK",
            kategori = "PUPUK",
            stok = 268,
            lokasi = "Blok A (Lemari Kunci Depan)",
            keterangan = "-",
            imageUrl = "https://picsum.photos/seed/pupuk1/200/200"
        ),
        BarangModel(
            sku = "BRG05",
            nama = "Palu",
            kategori = "ALAT KERJA",
            stok = 2037,
            lokasi = "Blok C (Rak Besi Heavy-Duty)",
            keterangan = "-",
            imageUrl = "https://picsum.photos/seed/palu1/200/200"
        ),
        BarangModel(
            sku = "BRG03",
            nama = "Cangkul",
            kategori = "ALAT KERJA",
            stok = 51,
            lokasi = "Blok A (Lemari Kunci Depan)",
            keterangan = "ini untuk disimpan aja",
            imageUrl = "https://picsum.photos/seed/cangkul1/200/200"
        ),
        BarangModel(
            sku = "BRG02",
            nama = "Pupuk",
            kategori = "PUPUK",
            stok = 32,
            lokasi = "Blok A (Lemari Kunci Depan)",
            keterangan = "Barangnya berat, hati-hati",
            imageUrl = "https://picsum.photos/seed/pupuk2/200/200"
        )
    )

    private var currentQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKelolaBarangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Kelola Barang"
        }

        setupTabsAndViewPager()
        setupSearch()
        setupStatistikDanBadge()

        binding.btnTambahBarang.setOnClickListener {
            Toast.makeText(requireContext(), "Tombol Tambah Barang diklik", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupTabsAndViewPager() {
        val tabsAdapter = KelolaBarangTabsAdapter(this)
        binding.viewPagerBarang.adapter = tabsAdapter

        TabLayoutMediator(binding.tabLayoutBarang, binding.viewPagerBarang) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Semua"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_inventory)
                }
                1 -> {
                    tab.text = "Pupuk"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_pupuk)
                }
                2 -> {
                    tab.text = "Alat Kerja"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_alat)
                }
            }
        }.attach()
    }

    private fun setupSearch() {
        binding.searchViewBarang.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                currentQuery = query?.trim() ?: ""
                notifyAllTabs()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText?.trim() ?: ""
                notifyAllTabs()
                return true
            }
        })
    }

    fun getFilteredBarang(kategoriFilter: String): List<BarangModel> {
        val keyword = currentQuery.lowercase()
        return daftarBarang.filter { item ->
            val matchKategori = kategoriFilter == "SEMUA" ||
                    item.kategori.equals(kategoriFilter, ignoreCase = true)
            val matchSearch = keyword.isEmpty() ||
                    item.nama.lowercase().contains(keyword) ||
                    item.sku.lowercase().contains(keyword)
            matchKategori && matchSearch
        }
    }

    private fun notifyAllTabs() {
        childFragmentManager.fragments.forEach { fragment ->
            if (fragment is TabBarangFragment) {
                fragment.refreshList()
            }
        }
    }

    private fun setupStatistikDanBadge() {
        binding.tvTotalJenisBarang.text = "${daftarBarang.size} Items"
        binding.tvTotalJumlahStok.text = "${daftarBarang.sumOf { it.stok }} Unit"

        val countSemua = daftarBarang.size
        val countPupuk = daftarBarang.count { it.kategori.equals("PUPUK", ignoreCase = true) }
        val countAlat = daftarBarang.count { it.kategori.equals("ALAT KERJA", ignoreCase = true) }

        binding.tabLayoutBarang.getTabAt(0)?.getOrCreateBadge()?.apply {
            isVisible = true
            number = countSemua
        }
        binding.tabLayoutBarang.getTabAt(1)?.getOrCreateBadge()?.apply {
            isVisible = true
            number = countPupuk
        }
        binding.tabLayoutBarang.getTabAt(2)?.getOrCreateBadge()?.apply {
            isVisible = true
            number = countAlat
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
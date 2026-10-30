package com.example.ilw_pocky.kelola_barang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.ilw_pocky.databinding.FragmentKelolaBarangBinding

class KelolaBarangFragment : Fragment() {

    private var _binding: FragmentKelolaBarangBinding? = null
    private val binding get() = _binding!!

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

        binding.searchViewBarang.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterData(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterData(newText)
                return true
            }
        })
    }

    private fun filterData(query: String?) {
        val searchText = query?.lowercase()?.trim() ?: ""

        val nama1 = binding.tvNama1.text.toString().lowercase()
        val sku1 = binding.tvSku1.text.toString().lowercase()

        val nama2 = binding.tvNama2.text.toString().lowercase()
        val sku2 = binding.tvSku2.text.toString().lowercase()

        // Pengecekan visibilitas tiap card
        val isCard1Match = nama1.contains(searchText) || sku1.contains(searchText)
        val isCard2Match = nama2.contains(searchText) || sku2.contains(searchText)

        if (searchText.isEmpty()) {
            binding.cardBarang1.visibility = View.VISIBLE
            binding.cardBarang2.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.GONE
        } else {
            binding.cardBarang1.visibility = if (isCard1Match) View.VISIBLE else View.GONE
            binding.cardBarang2.visibility = if (isCard2Match) View.VISIBLE else View.GONE

            // Jika tidak ada satu pun barang yang cocok, tampilkan teks "Barang tidak ditemukan"
            if (!isCard1Match && !isCard2Match) {
                binding.tvEmptyState.visibility = View.VISIBLE
            } else {
                binding.tvEmptyState.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
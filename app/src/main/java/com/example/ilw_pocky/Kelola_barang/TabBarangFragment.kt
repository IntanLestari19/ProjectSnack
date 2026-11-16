package com.example.ilw_pocky.kelola_barang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ilw_pocky.databinding.FragmentTabBarangBinding

class TabBarangFragment : Fragment() {

    private var _binding: FragmentTabBarangBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: BarangAdapter
    private var kategoriFilter: String = "SEMUA"

    companion object {
        private const val ARG_KATEGORI = "arg_kategori"

        fun newInstance(kategori: String): TabBarangFragment {
            return TabBarangFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_KATEGORI, kategori)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kategoriFilter = arguments?.getString(ARG_KATEGORI) ?: "SEMUA"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBarangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BarangAdapter(emptyList())
        binding.rvBarang.layoutManager = LinearLayoutManager(requireContext())
        binding.rvBarang.adapter = adapter

        refreshList()
    }

    fun refreshList() {
        if (_binding == null) return
        val parent = parentFragment as? KelolaBarangFragment ?: return
        val filtered = parent.getFilteredBarang(kategoriFilter)
        adapter.updateData(filtered)

        binding.tvEmptyTab.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        binding.rvBarang.visibility = if (filtered.isEmpty()) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.ilw_pocky.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.ilw_pocky.R
import com.example.ilw_pocky.databinding.FragmentHomeBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Model data dummy, mengikuti struktur kartu di Kelola Barang
    private data class BarangDummy(
        val nama: String,
        val sku: String,
        val kategori: String,
        val stok: Float
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar judul & subtitle
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.app_sawit)
            subtitle = getString(R.string.tagline)
        }

        // Chart stok barang (data dummy dari string Kelola Barang)
        setupBarChart()

        // Akses SharedPreferences
        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)

        // Tombol ke Profil Developer
        binding.btnProfil.setOnClickListener {
            startActivity(Intent(requireContext(), ProfilActivity::class.java))
        }

        // Tombol ke AlatPanenActivity (WebView)
        binding.btnWeb.setOnClickListener {
            startActivity(Intent(requireContext(), AlatPanenActivity::class.java))
        }

        // Tombol Logout
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    sharedPref.edit().clear().apply()
                    dialog.dismiss()
                    requireActivity().finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun parseStok(teks: String): Float {
        val angka = Regex("\\d[\\d.,]*").find(teks)?.value ?: return 0f
        return angka.replace(".", "").replace(",", "").toFloatOrNull() ?: 0f
    }
    private fun getDataBarangDummy(): List<BarangDummy> = listOf(
        BarangDummy(
            nama = getString(R.string.kb_item1_nama),
            sku = getString(R.string.kb_item1_sku),
            kategori = getString(R.string.kb_item1_kat),
            stok = parseStok(getString(R.string.kb_item1_stok))
        ),
        BarangDummy(
            nama = getString(R.string.kb_item2_nama),
            sku = getString(R.string.kb_item2_sku),
            kategori = getString(R.string.kb_item2_kat),
            stok = parseStok(getString(R.string.kb_item2_stok))
        )
    )

    private fun setupBarChart() {
        val dataBarang = getDataBarangDummy()

        // 1. BarEntry (x = index, y = stok)
        val entries = ArrayList<BarEntry>()
        dataBarang.forEachIndexed { index, barang ->
            entries.add(BarEntry(index.toFloat(), barang.stok))
        }

        // 2. Dataset + styling
        val dataSet = BarDataSet(entries, getString(R.string.chart_label)).apply {
            color = ContextCompat.getColor(requireContext(), R.color.primary_blue)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.text_black)
            valueTextSize = 11f
        }

        // 3. Pasang ke chart
        val barData = BarData(dataSet).apply { barWidth = 0.5f }

        binding.barChartBarang.apply {
            data = barData

            description.isEnabled = false
            setFitBars(true)
            setDrawGridBackground(false)
            setScaleEnabled(false)
            setPinchZoom(false)
            legend.textColor = ContextCompat.getColor(requireContext(), R.color.text_gray)

            // Sumbu X: nama barang
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = IndexAxisValueFormatter(dataBarang.map { it.nama })
                granularity = 1f
                labelCount = dataBarang.size
                setDrawGridLines(false)
                textSize = 10f
                textColor = ContextCompat.getColor(requireContext(), R.color.text_black)
            }

            // Sumbu Y
            axisLeft.apply {
                axisMinimum = 0f
                textColor = ContextCompat.getColor(requireContext(), R.color.text_gray)
            }
            axisRight.isEnabled = false

            animateY(1000)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
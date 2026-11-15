package com.example.ilw_pocky.manajemen_pengguna

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ilw_pocky.databinding.FragmentManajemenPenggunaBinding
import com.google.android.material.snackbar.Snackbar

class ManajemenPenggunaFragment : Fragment() {
    private var _binding: FragmentManajemenPenggunaBinding? = null
    private val binding get() = _binding!!

    // Menggunakan mutableListOf agar data bisa ditambah/diedit/dihapus secara dinamis
    private val daftarPengguna = mutableListOf(
        PenggunaModel("Budi Santoso", "Pemilik • Aktif", "https://randomuser.me/api/portraits/men/1.jpg"),
        PenggunaModel("Siti Rahma", "Karyawan • Aktif", "https://randomuser.me/api/portraits/women/2.jpg"),
        PenggunaModel("Andi Pratama", "Supplier • Nonaktif", "https://randomuser.me/api/portraits/men/3.jpg"),
        PenggunaModel("Dewi Lestari", "Karyawan • Aktif", "https://randomuser.me/api/portraits/women/4.jpg"),
        PenggunaModel("Rizky Saputra", "Supplier • Aktif", "https://randomuser.me/api/portraits/men/5.jpg")
    )

    private lateinit var adapter: PenggunaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManajemenPenggunaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Manajemen Pengguna"
        }

        adapter = PenggunaAdapter(
            context = requireContext(),
            daftarPengguna = daftarPengguna,
            onEditClick = { pengguna, position ->
                Toast.makeText(
                    requireContext(),
                    "Edit data: ${pengguna.nama} (Posisi ke-${position + 1})",
                    Toast.LENGTH_SHORT
                ).show()
                // Di sini Anda bisa membuka halaman/dialog Edit Pengguna
            },
            onHapusClick = { pengguna, position ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Pengguna")
                    .setMessage("Yakin ingin menghapus ${pengguna.nama}?")
                    .setPositiveButton("Hapus") { _, _ ->
                        daftarPengguna.removeAt(position)
                        adapter.notifyDataSetChanged()
                        Snackbar.make(
                            binding.root,
                            "${pengguna.nama} berhasil dihapus",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            },
            onItemClick = { pengguna ->
                Snackbar.make(
                    binding.root,
                    "${pengguna.nama} (${pengguna.peranDanStatus})",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        )

        binding.listViewPengguna.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.ilw_pocky.manajemen_pengguna

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.ilw_pocky.R
import com.example.ilw_pocky.databinding.ItemPenggunaBinding

class PenggunaAdapter(
    context: Context,
    private val daftarPengguna: MutableList<PenggunaModel>,
    private val onEditClick: (PenggunaModel, Int) -> Unit,
    private val onHapusClick: (PenggunaModel, Int) -> Unit,
    private val onItemClick: (PenggunaModel) -> Unit
) : ArrayAdapter<PenggunaModel>(context, 0, daftarPengguna) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemPenggunaBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        val view = binding.root
        val data = daftarPengguna[position]

        // Memuat gambar avatar menggunakan Glide
        Glide.with(context)
            .load(data.avatarUrl)
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
            .into(binding.imgAvatar)

        binding.tvNamaPengguna.text = data.nama
        binding.tvPeranStatus.text = data.peranDanStatus

        // Aksi klik seluruh kartu
        view.setOnClickListener {
            onItemClick(data)
        }

        // Aksi klik tombol Edit
        binding.btnEdit.setOnClickListener {
            onEditClick(data, position)
        }

        // Aksi klik tombol Hapus
        binding.btnHapus.setOnClickListener {
            onHapusClick(data, position)
        }

        return view
    }
}
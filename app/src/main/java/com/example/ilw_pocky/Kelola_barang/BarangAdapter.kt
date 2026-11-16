package com.example.ilw_pocky.kelola_barang

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ilw_pocky.R
import com.example.ilw_pocky.databinding.ItemBarangBinding

class BarangAdapter(
    private var listBarang: List<BarangModel>
) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>() {

    inner class BarangViewHolder(val binding: ItemBarangBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val binding = ItemBarangBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BarangViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val item = listBarang[position]
        val context = holder.itemView.context

        with(holder.binding) {
            tvNamaBarang.text = item.nama
            tvSkuBarang.text = "ID SKU: ${item.sku}"
            tvKategoriBadge.text = item.kategori
            tvStokBarang.text = "${item.stok} Unit"
            tvLokasiBarang.text = "Lokasi: ${item.lokasi}"
            tvKeteranganBarang.text = "Keterangan: ${item.keterangan}"

            val badgeColor = if (item.kategori.equals("PUPUK", ignoreCase = true)) {
                R.color.primary_blue
            } else {
                R.color.logo_hijau
            }
            tvKategoriBadge.setTextColor(ContextCompat.getColor(context, badgeColor))

            Glide.with(context)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_inventory)
                .error(R.drawable.ic_inventory)
                .into(imgBarang)

            root.setOnClickListener {
                Toast.makeText(context, "Anda memilih ${item.nama} (${item.sku})", Toast.LENGTH_SHORT).show()
            }

            btnEditBarang.setOnClickListener {
                Toast.makeText(context, "Tombol Edit ${item.nama} diklik", Toast.LENGTH_SHORT).show()
            }

            btnHapusBarang.setOnClickListener {
                Toast.makeText(context, "Tombol Hapus ${item.nama} diklik", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = listBarang.size

    fun updateData(newList: List<BarangModel>) {
        listBarang = newList
        notifyDataSetChanged()
    }
}
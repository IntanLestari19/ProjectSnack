package com.example.ilw_pocky.kelola_barang

data class BarangModel(
    val sku: String,
    var nama: String,
    var kategori: String, // "PUPUK" atau "ALAT KERJA"
    var stok: Int,
    var lokasi: String,
    var keterangan: String,
    val imageUrl: String
)
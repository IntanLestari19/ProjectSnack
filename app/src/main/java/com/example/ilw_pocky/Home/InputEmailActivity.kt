package com.example.ilw_pocky.Home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ilw_pocky.databinding.ActivityInputEmailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InputEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLanjutRegis.setOnClickListener {
            val email = binding.etGmailInput.text.toString().trim()

            // Validasi Soal 1: Tidak boleh kosong & harus mengandung domain @gmail.com
            if (email.isEmpty()) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Error Validasi")
                    .setMessage("Email tidak boleh kosong!")
                    .setPositiveButton("OK", null)
                    .show()
            } else if (!email.endsWith("@gmail.com")) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Error Validasi")
                    .setMessage("Email harus menggunakan domain @gmail.com!")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                // Jika valid: Pindah ke RegisActivity membawa data email via Intent Extra
                val intent = Intent(this, RegisActivity::class.java)
                intent.putExtra("EXTRA_EMAIL", email)
                startActivity(intent)
                finish()
            }
        }
    }
}
package com.example.ilw_pocky.Home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ilw_pocky.R
import com.example.ilw_pocky.databinding.ActivityInputEmailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InputEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.gmail_heading)
            subtitle = getString(R.string.app_sawit)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.white))

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
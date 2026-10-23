package com.example.ilw_pocky.Home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ilw_pocky.R
import com.example.ilw_pocky.databinding.ActivityRegisBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_regis)
            subtitle = getString(R.string.app_sawit)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // 1. Ambil email dari Intent (dikirim dari InputEmailActivity)
        val passedEmail = intent.getStringExtra("EXTRA_EMAIL") ?: ""
        if (passedEmail.isNotEmpty()) {
            binding.etEmail.setText(passedEmail)
            binding.etEmail.isEnabled = false
            binding.etEmail.isFocusable = false
        }

        // 2. Tombol Registrasi Ditekan
        binding.btnRegis.setOnClickListener {
            val nama = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Validasi Soal 2
            when {
                nama.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Validasi Gagal")
                        .setMessage("Semua field wajib diisi!")
                        .setPositiveButton("OK", null)
                        .show()
                }
                username.contains(" ") -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Validasi Gagal")
                        .setMessage("Username tidak boleh mengandung spasi!")
                        .setPositiveButton("OK", null)
                        .show()
                }
                password.length < 6 -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Validasi Gagal")
                        .setMessage("Password minimal 6 karakter!")
                        .setPositiveButton("OK", null)
                        .show()
                }
                else -> {
                    // Simpan seluruh data registrasi ke SharedPreferences
                    val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("reg_nama", nama)
                    editor.putString("reg_email", email)
                    editor.putString("reg_username", username)
                    editor.putString("reg_password", password)
                    editor.apply()

                    // Tampilkan informasi "Registrasi Berhasil"
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Informasi")
                        .setMessage("Registrasi Berhasil")
                        .setPositiveButton("OK") { _, _ ->
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        .show()
                }
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
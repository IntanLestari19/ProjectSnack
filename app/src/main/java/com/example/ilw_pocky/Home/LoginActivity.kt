package com.example.ilw_pocky.Home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ilw_pocky.BaseActivity
import com.example.ilw_pocky.R
import com.example.ilw_pocky.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.title_login)
            subtitle = getString(R.string.app_sawit)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Klik Tombol Register With Gmail (Soal 1)
        binding.btnRegisterGmail.setOnClickListener {
            val intent = Intent(this, InputEmailActivity::class.java)
            startActivity(intent)
        }

        // Klik Tombol Login (Soal 2)
        binding.btnLogin.setOnClickListener {
            val username = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            val savedUser = sharedPref.getString("reg_username", "")
            val savedPass = sharedPref.getString("reg_password", "")

            // Kondisi 1: username == password
            val isCondition1 = username.isNotEmpty() && username == password
            // Kondisi 2: username & password sesuai data SharedPreferences
            val isCondition2 = username.isNotEmpty() && username == savedUser && password == savedPass

            if (isCondition1 || isCondition2) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                startActivity(Intent(this, BaseActivity::class.java))
                finish()
            } else {
                // Tampilkan error menggunakan MaterialAlertDialog
                MaterialAlertDialogBuilder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau Password salah!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        binding.tvToRegis.setOnClickListener {
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)
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
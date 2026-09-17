package com.example.asistentecompras

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.asistentecompras.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val EXTRA_NOMBRE = "extra_nombre"
        const val EXTRA_CORREO = "extra_correo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener {
            validarYRegistrar()
        }
    }

    private fun validarYRegistrar() {
        // Limpiar errores previos
        binding.tilNombre.error = null
        binding.tilCorreo.error = null

        val nombre = binding.etNombre.text?.toString()?.trim().orEmpty()
        val correo = binding.etCorreo.text?.toString()?.trim().orEmpty()

        var esValido = true

        if (nombre.isEmpty()) {
            binding.tilNombre.error = getString(R.string.error_nombre_vacio)
            esValido = false
        }

        if (correo.isEmpty()) {
            binding.tilCorreo.error = getString(R.string.error_correo_vacio)
            esValido = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.tilCorreo.error = getString(R.string.error_correo_invalido)
            esValido = false
        }

        if (!esValido) return

        val intent = Intent(this, MainTabsActivity::class.java).apply {
            putExtra(EXTRA_NOMBRE, nombre)
            putExtra(EXTRA_CORREO, correo)
        }
        startActivity(intent)
        finish()
    }
}

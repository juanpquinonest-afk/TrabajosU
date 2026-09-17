package com.example.asistentecompras

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.example.asistentecompras.databinding.ActivityMainTabsBinding


class MainTabsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainTabsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTabsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val nombre = intent.getStringExtra(MainActivity.EXTRA_NOMBRE) ?: ""
        binding.tvSaludo.text = getString(R.string.saludo_usuario, nombre)

        val titulosTabs = listOf(
            getString(R.string.tab_descuentos),
            getString(R.string.tab_dividir_cuenta),
            getString(R.string.tab_compra_cuotas)
        )

        binding.viewPager.adapter = TabsPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titulosTabs[position]
        }.attach()
    }
}

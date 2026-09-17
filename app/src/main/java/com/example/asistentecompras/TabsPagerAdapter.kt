package com.example.asistentecompras

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescuentosFragment()
            1 -> DividirCuentaFragment()
            2 -> ComprasCuotasFragment()
            else -> throw IllegalArgumentException("Posición de Tab inválida: $position")
        }
    }
}

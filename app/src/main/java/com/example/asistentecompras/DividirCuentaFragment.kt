package com.example.asistentecompras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.asistentecompras.databinding.FragmentDividirCuentaBinding
class DividirCuentaFragment : Fragment() {

    private var _binding: FragmentDividirCuentaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDividirCuentaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCalcularDivision.setOnClickListener {
            calcularDivision()
        }
    }

    private fun calcularDivision() {
        binding.tilValorCuenta.error = null
        binding.tilNumeroPersonas.error = null
        binding.tilPorcentajePropina.error = null

        val valorCuenta = Utils.aDoubleOrNull(binding.etValorCuenta.text?.toString())
        val numeroPersonas = Utils.aIntOrNull(binding.etNumeroPersonas.text?.toString())
        val porcentajePropina = Utils.aDoubleOrNull(binding.etPorcentajePropina.text?.toString())

        var esValido = true

        if (valorCuenta == null) {
            binding.tilValorCuenta.error = getString(R.string.error_campo_obligatorio)
            esValido = false
        } else if (valorCuenta <= 0) {
            binding.tilValorCuenta.error = getString(R.string.error_valor_positivo)
            esValido = false
        }

        if (numeroPersonas == null) {
            binding.tilNumeroPersonas.error = getString(R.string.error_campo_obligatorio)
            esValido = false
        } else if (numeroPersonas <= 0) {
            binding.tilNumeroPersonas.error = getString(R.string.error_valor_positivo)
            esValido = false
        }

        if (porcentajePropina == null) {
            binding.tilPorcentajePropina.error = getString(R.string.error_campo_obligatorio)
            esValido = false
        } else if (porcentajePropina < 0 || porcentajePropina > 100) {
            binding.tilPorcentajePropina.error = getString(R.string.error_porcentaje_rango)
            esValido = false
        }

        if (!esValido || valorCuenta == null || numeroPersonas == null || porcentajePropina == null) return

        val valorPropina = valorCuenta * (porcentajePropina / 100.0)
        val totalConPropina = valorCuenta + valorPropina
        val valorPorPersona = totalConPropina / numeroPersonas

        binding.tvPropina.text = Utils.formatoMoneda(valorPropina)
        binding.tvTotalConPropina.text = Utils.formatoMoneda(totalConPropina)
        binding.tvValorPorPersona.text = Utils.formatoMoneda(valorPorPersona)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

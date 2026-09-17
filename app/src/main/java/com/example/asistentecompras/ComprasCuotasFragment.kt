package com.example.asistentecompras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.asistentecompras.databinding.FragmentComprasCuotasBinding
import kotlin.math.pow
class ComprasCuotasFragment : Fragment() {

    private var _binding: FragmentComprasCuotasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComprasCuotasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCalcularCuotas.setOnClickListener {
            calcularCuotas()
        }
    }

    private fun calcularCuotas() {
        binding.tilValorProducto.error = null
        binding.tilNumeroCuotas.error = null
        binding.tilTasaInteres.error = null

        val valorProducto = Utils.aDoubleOrNull(binding.etValorProducto.text?.toString())
        val numeroCuotas = Utils.aIntOrNull(binding.etNumeroCuotas.text?.toString())
        val tasaInteres = Utils.aDoubleOrNull(binding.etTasaInteres.text?.toString())

        var esValido = true

        // Validación: valor del producto obligatorio y mayor que cero
        if (valorProducto == null) {
            binding.tilValorProducto.error = getString(R.string.error_campo_obligatorio)
            esValido = false
        } else if (valorProducto <= 0) {
            binding.tilValorProducto.error = getString(R.string.error_valor_positivo)
            esValido = false
        }

        if (numeroCuotas == null) {
            binding.tilNumeroCuotas.error = getString(R.string.error_campo_obligatorio)
            esValido = false
        } else if (numeroCuotas <= 0) {
            binding.tilNumeroCuotas.error = getString(R.string.error_valor_positivo)
            esValido = false
        }

        if (tasaInteres == null) {
            binding.tilTasaInteres.error = getString(R.string.error_campo_obligatorio)
            esValido = false
        } else if (tasaInteres < 0) {
            binding.tilTasaInteres.error = getString(R.string.error_valor_no_negativo)
            esValido = false
        }

        if (!esValido || valorProducto == null || numeroCuotas == null || tasaInteres == null) return

        val tasaDecimal = tasaInteres / 100.0
        val totalFinanciado = valorProducto * (1.0 + tasaDecimal).pow(numeroCuotas)
        val valorCuota = totalFinanciado / numeroCuotas

        binding.tvValorOriginal.text = Utils.formatoMoneda(valorProducto)
        binding.tvTotalFinanciado.text = Utils.formatoMoneda(totalFinanciado)
        binding.tvValorCuota.text = Utils.formatoMoneda(valorCuota)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.asistentecompras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.asistentecompras.databinding.FragmentDescuentosBinding

class DescuentosFragment : Fragment() {

    private var _binding: FragmentDescuentosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescuentosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCalcularDescuento.setOnClickListener {
            calcularDescuento()
        }
    }

    private fun calcularDescuento() {
        binding.tilPrecioOriginal.error = null
        binding.tilPorcentajeDescuento.error = null

        val precioOriginal = Utils.aDoubleOrNull(binding.etPrecioOriginal.text?.toString())
        val porcentajeDescuento = Utils.aDoubleOrNull(binding.etPorcentajeDescuento.text?.toString())

        var esValido = true

        if (precioOriginal == null) {
            binding.tilPrecioOriginal.error = getString(R.string.error_campo_obligatorio)
            esValido = false
        } else if (precioOriginal <= 0) {
            binding.tilPrecioOriginal.error = getString(R.string.error_valor_positivo)
            esValido = false
        }

        if (porcentajeDescuento == null) {
            binding.tilPorcentajeDescuento.error = getString(R.string.error_campo_obligatorio)
            esValido = false
        } else if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            binding.tilPorcentajeDescuento.error = getString(R.string.error_porcentaje_rango)
            esValido = false
        }

        if (!esValido || precioOriginal == null || porcentajeDescuento == null) return

        val valorDescuento = precioOriginal * (porcentajeDescuento / 100.0)
        val totalAPagar = precioOriginal - valorDescuento

        binding.tvDescuentoAplicado.text = Utils.formatoMoneda(valorDescuento)
        binding.tvTotalPagar.text = Utils.formatoMoneda(totalAPagar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.asistentecompras

import java.text.NumberFormat
import java.util.Locale

/**
 * Funciones auxiliares compartidas por los distintos Tabs de la aplicación.
 */
object Utils {

    /**
     * Formatea un valor numérico como moneda en pesos colombianos,
     * sin decimales, por ejemplo: 170000.0 -> "$ 170.000"
     */
    fun formatoMoneda(valor: Double): String {
        val formato = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
        formato.maximumFractionDigits = 0
        formato.minimumFractionDigits = 0
        return formato.format(valor)
    }

    /**
     * Convierte un texto ingresado por el usuario a Double.
     * Retorna null si el texto está vacío o no es un número válido.
     */
    fun aDoubleOrNull(texto: String?): Double? {
        if (texto.isNullOrBlank()) return null
        return texto.trim().replace(",", ".").toDoubleOrNull()
    }

    /**
     * Convierte un texto ingresado por el usuario a Int.
     * Retorna null si el texto está vacío o no es un número entero válido.
     */
    fun aIntOrNull(texto: String?): Int? {
        if (texto.isNullOrBlank()) return null
        return texto.trim().toIntOrNull()
    }
}

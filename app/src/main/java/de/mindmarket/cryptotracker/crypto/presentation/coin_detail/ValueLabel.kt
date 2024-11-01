package de.mindmarket.cryptotracker.crypto.presentation.coin_detail

import java.text.NumberFormat
import java.util.Locale

data class ValueLabel(
    val value: Float,
    val unit: String
) {
    fun formatted(): String {
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            val fractionDigits = when {
                value > 1000 -> 0 // no digits for high prices like Btc price
                value in 2f..999f -> 2 // 2 digits
                else -> 3
            }
            maximumFractionDigits = fractionDigits
            minimumFractionDigits = 0
        }
        return "${formatter.format(value)}$unit"
    }
}

package sk.himdeve.formatter

import java.text.NumberFormat

/**
 * Created by Robin Himdeve on 2/25/2022.
 */
fun Double.format(dropZeroFraction: Boolean = false): String {
    val formatted = doubleFormatter.format(this)
    return adjustZeroFraction(formatted, dropZeroFraction)
}

private val doubleFormatter = NumberFormat.getNumberInstance().apply {
    minimumFractionDigits = 2
    maximumFractionDigits = 2
}

private fun adjustZeroFraction(formattedNumber: String, dropZeroFraction: Boolean): String {
    return if (dropZeroFraction && formattedNumber.endsWith("00")) {
        formattedNumber.substring(0, formattedNumber.length - 3)
    } else {
        formattedNumber
    }
}
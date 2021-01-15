package com.hfad.worldskillsbank

import java.lang.StringBuilder

class NumberFormatter {
    companion object {
        fun formatNumber(num: String, numbersBegin: Int, numbersEnd: Int): String {
            val formattedNum = StringBuilder(num.substring(0 until numbersBegin))
            repeat(num.length - numbersBegin - numbersEnd) { formattedNum.append("*") }
            formattedNum.append(num.substring(num.length - numbersEnd, num.length))
            return formattedNum.toString()
        }
    }
}
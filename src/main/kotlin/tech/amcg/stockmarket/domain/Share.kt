package tech.amcg.stockmarket.domain

import java.math.BigDecimal

interface Share {

    val stockIdentifier: StockIdentifier
    val lastDividend: BigDecimal
    val parValue: BigDecimal

    fun calculateDividendYield(price: BigDecimal): BigDecimal

    fun calculatePeRatio(price: BigDecimal): BigDecimal {
        return price.div(lastDividend)
    }

}
package tech.amcg.stockmarket.domain

import java.math.BigDecimal

data class PreferredShare(
    override val stockIdentifier: StockIdentifier,
    override val lastDividend: BigDecimal,
    override val parValue: BigDecimal,
    val fixedDividend: BigDecimal
) : Share {

    override fun calculateDividendYield(price: BigDecimal): BigDecimal {
        return fixedDividend.times(parValue).div(price)
    }

}

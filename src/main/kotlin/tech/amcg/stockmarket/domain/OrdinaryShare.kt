package tech.amcg.stockmarket.domain

import java.math.BigDecimal

data class OrdinaryShare(
    override val stockIdentifier: StockIdentifier,
    override val lastDividend: BigDecimal,
    override val parValue: BigDecimal
) : Share {

    override fun calculateDividendYield(price: BigDecimal): BigDecimal {
        return lastDividend.div(price)
    }

}
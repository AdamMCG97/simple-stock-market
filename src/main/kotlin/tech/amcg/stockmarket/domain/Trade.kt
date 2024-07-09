package tech.amcg.stockmarket.domain

import java.math.BigDecimal
import java.time.Instant

data class Trade(
    val identifier: StockIdentifier,
    val timestamp: Instant,
    val quantity: BigDecimal,
    val indicator: Indicator,
    val price: BigDecimal
)

package tech.amcg.stockmarket.service

import tech.amcg.stockmarket.datastore.trade.TradeStore
import tech.amcg.stockmarket.domain.StockIdentifier
import java.math.BigDecimal
import java.time.Instant
import kotlin.math.pow

class CalculationService(private val tradeStore: TradeStore) {

    fun volumeWeightedStockPrice(stockIdentifier: StockIdentifier, asOf: Instant): BigDecimal {
        return tradeStore.getTrades(stockIdentifier, asOf).let { tradeList ->
            val numerator = tradeList.sumOf { it.price.times(it.quantity) }
            val denominator = tradeList.sumOf { it.quantity }
            if (denominator != BigDecimal.ZERO) {
                numerator.div(denominator)
            } else {
                BigDecimal.ZERO
            }
        }
    }

    fun geometricMean(): Map<StockIdentifier, Double> {
        return tradeStore.getAllTrades().map { tradeList ->
            val product = tradeList.value.map { it.quantity }.reduce { s, t -> s * t }
            tradeList.key to product.toDouble().pow(1.toDouble().div(tradeList.value.size))
        }.toMap()
    }

}
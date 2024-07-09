package tech.amcg.stockmarket.fixtures

import tech.amcg.stockmarket.datastore.trade.TradeStore
import tech.amcg.stockmarket.domain.Indicator
import tech.amcg.stockmarket.domain.StockIdentifier
import tech.amcg.stockmarket.domain.Trade
import java.math.BigDecimal
import java.time.Instant

fun aTrade(
    identifier: StockIdentifier = StockIdentifier.COF,
    timestamp: Instant = Instant.now(),
    quantity: BigDecimal = BigDecimal.TEN,
    indicator: Indicator = Indicator.BUY,
    price: BigDecimal = BigDecimal.ONE
): Trade {
    return Trade(
        identifier, timestamp, quantity, indicator, price
    )
}

fun aStubTradeStore(
    getAllTrades: Map<StockIdentifier, List<Trade>> = emptyMap(),
    getTrades: List<Trade> = listOf(aTrade(), aTrade())
): TradeStore {
    return object : TradeStore {
        override fun save(trade: Trade): Boolean {
            TODO("Not yet implemented")
        }
        override fun getAllTrades(): Map<StockIdentifier, List<Trade>> {
            return getAllTrades
        }
        override fun getTrades(identifier: StockIdentifier, asOf: Instant): List<Trade> {
            return getTrades
        }
    }
}
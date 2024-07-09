package tech.amcg.stockmarket.datastore.trade

import tech.amcg.stockmarket.domain.StockIdentifier
import tech.amcg.stockmarket.domain.Trade
import java.time.Instant

class InMemoryTradeStore : TradeStore {

    private val trades = mutableMapOf<StockIdentifier, List<Trade>>()

    override fun save(trade: Trade): Boolean {
        trades.compute(trade.identifier) { _, v -> v?.plus(trade) ?: listOf(trade) }
        return true
    }

    override fun getAllTrades(): Map<StockIdentifier, List<Trade>> {
        return trades
    }

    override fun getTrades(identifier: StockIdentifier, asOf: Instant): List<Trade> {
        return trades.get(identifier)?.filter { it.timestamp.isAfter(asOf) } ?: emptyList()
    }
}
package tech.amcg.stockmarket.datastore.trade

import tech.amcg.stockmarket.domain.StockIdentifier
import tech.amcg.stockmarket.domain.Trade
import java.time.Instant

interface TradeStore {

    fun save(trade: Trade): Boolean

    fun getAllTrades(): Map<StockIdentifier, List<Trade>>

    fun getTrades(identifier: StockIdentifier, asOf: Instant): List<Trade>

}
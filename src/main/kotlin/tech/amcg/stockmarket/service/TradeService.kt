package tech.amcg.stockmarket.service

import tech.amcg.stockmarket.datastore.trade.TradeStore
import tech.amcg.stockmarket.domain.Trade

class TradeService(private val tradeStore: TradeStore) {

    fun saveTrade(trade: Trade): Boolean {
//        Build in some re-try mechanism etc if failure
        return tradeStore.save(trade)
    }

}
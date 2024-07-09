package tech.amcg.stockmarket.datastore.trade

import org.junit.jupiter.api.Test
import tech.amcg.stockmarket.domain.StockIdentifier
import tech.amcg.stockmarket.fixtures.aTrade
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class InMemoryTradeStoreTest {

    val tradeStore = InMemoryTradeStore()

    @Test
    fun `can save a new trade`() {
        val trade = aTrade()

        tradeStore.save(trade)

        tradeStore.getAllTrades().let {
            assertEquals(1, it.size)
            assertEquals(listOf(trade), it.get(trade.identifier))
        }

    }

    @Test
    fun `can save many trades`() {
        val cofTrade1 = aTrade()
        val cofTrade2 = aTrade()
        val juiTrade = aTrade(identifier = StockIdentifier.JUI)
        val juiTrade2 = aTrade(identifier = StockIdentifier.JUI)
        val cofTrade3 = aTrade()


        listOf(cofTrade1, cofTrade2, juiTrade, juiTrade2, cofTrade3).forEach {
            tradeStore.save(it)
        }

        tradeStore.getAllTrades().let {
            assertEquals(2, it.size)
            assertEquals(listOf(cofTrade1, cofTrade2, cofTrade3), it.get(StockIdentifier.COF))
            assertEquals(listOf(juiTrade, juiTrade2), it.get(StockIdentifier.JUI))
        }

    }

    @Test
    fun `can get trades asof specified instant`() {
        val cofTrade1 = aTrade()
        val cofTrade2 = aTrade(timestamp = Instant.now().minusSeconds(1.days.inWholeSeconds))
        val juiTrade = aTrade(identifier = StockIdentifier.JUI)
        val cofTrade3 = aTrade()


        listOf(cofTrade1, cofTrade2, juiTrade, cofTrade3).forEach {
            tradeStore.save(it)
        }

        tradeStore.getTrades(StockIdentifier.COF, Instant.now().minusSeconds(10.minutes.inWholeSeconds)).let {
            assertEquals(listOf(cofTrade1, cofTrade3), it)
        }

        tradeStore.getTrades(StockIdentifier.JUI, Instant.now().minusSeconds(10.minutes.inWholeSeconds)).let {
            assertEquals(listOf(juiTrade), it)
        }

    }

}
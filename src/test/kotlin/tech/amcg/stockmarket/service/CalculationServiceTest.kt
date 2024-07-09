package tech.amcg.stockmarket.service

import org.junit.jupiter.api.Test
import tech.amcg.stockmarket.domain.StockIdentifier
import tech.amcg.stockmarket.domain.Trade
import tech.amcg.stockmarket.fixtures.aStubTradeStore
import tech.amcg.stockmarket.fixtures.aTrade
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.assertEquals

class CalculationServiceTest {

    @Test
    fun `Can calculate the geometric mean`() {
        val allTrades = mapOf(
            StockIdentifier.JUI to listOf(aTrade(identifier = StockIdentifier.JUI, quantity = BigDecimal("32")), aTrade(identifier = StockIdentifier.JUI, quantity = BigDecimal("2"))),
            StockIdentifier.COF to listOf(aTrade(quantity = BigDecimal("3")), aTrade(quantity = BigDecimal("3")), aTrade(quantity = BigDecimal("3")))
        )

        val tradeStore = aStubTradeStore(getAllTrades = allTrades)

        val calculationService = CalculationService(tradeStore)

        calculationService.geometricMean().let { returnedMap ->
            assertEquals(2, returnedMap.size)
            assertEquals(8.toDouble(), returnedMap.get(StockIdentifier.JUI))
            assertEquals(3.toDouble(), returnedMap.get(StockIdentifier.COF))
        }
    }

    @Test
    fun `Can calculate the geometric mean without any trades`() {
        val allTrades =  emptyMap<StockIdentifier, List<Trade>>()

        val tradeStore = aStubTradeStore(getAllTrades = allTrades)

        val calculationService = CalculationService(tradeStore)

        assertEquals(emptyMap(), calculationService.geometricMean())
    }

    @Test
    fun `can calculate volume weighted stock price`() {
        val trades = listOf(aTrade(quantity = BigDecimal(2), price = BigDecimal(50)), aTrade(quantity = BigDecimal(1), price = BigDecimal(80)))
        val tradeStore = aStubTradeStore(getTrades = trades)

        val calculationService = CalculationService(tradeStore)

        assertEquals(BigDecimal(60), calculationService.volumeWeightedStockPrice(StockIdentifier.COF, Instant.now()))
    }

    @Test
    fun `can calculate volume weighted stock price without any trades`() {
        val trades = emptyList<Trade>()
        val tradeStore = aStubTradeStore(getTrades = trades)

        val calculationService = CalculationService(tradeStore)

        assertEquals(BigDecimal(0), calculationService.volumeWeightedStockPrice(StockIdentifier.COF, Instant.now()))
    }

}
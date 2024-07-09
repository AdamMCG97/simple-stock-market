package tech.amcg.stockmarket.e2e

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import tech.amcg.stockmarket.cli.CommandFactory
import tech.amcg.stockmarket.cli.Controller
import tech.amcg.stockmarket.datastore.share.SampleShareStore
import tech.amcg.stockmarket.datastore.trade.InMemoryTradeStore
import tech.amcg.stockmarket.service.CalculationService
import tech.amcg.stockmarket.service.TradeService
import java.io.ByteArrayOutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.io.PrintStream
import java.time.Instant
import java.util.concurrent.Executors
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days


class EndToEndTest {

    val tradeStore = InMemoryTradeStore()
    val shareStore = SampleShareStore()

    val calculationService = CalculationService(tradeStore)
    val tradeService = TradeService(tradeStore)

    val commandFactory = CommandFactory(calculationService, tradeService, shareStore)

    val outputWritten = mutableListOf<String?>()

    val controller = Controller(commandFactory) {
        outputWritten.add(it)
    }

    @Test
    @Timeout(20)
    fun `Can save multiple trades and get updated VWSP`() {

        val input = """
            H
            TRADE TEA ${Instant.now()} 400 BUY 75
            VWSP TEA
            TRADE TEA ${Instant.now()} 200 BUY 100
            VWSP TEA
            TRADE TEA ${Instant.now()} 500 BUY 300
            VWSP TEA
            TRADE TEA ${Instant.now().minusSeconds(1.days.inWholeSeconds)} 5000 BUY 1000
            VWSP TEA
            TRADE COF ${Instant.now().minusSeconds(1.days.inWholeSeconds)} 5000 BUY 1000
            VWSP TEA
            VWSP COF
            TRADE COF ${Instant.now()} 500 BUY 300
            VWSP COF
            SI
            DY COF 50
            DY COF 275
            DY MIL 150
            PE JUI 125
            PE JUI 535
            PE COF 160
            Q
        """.trimIndent()

        val pipedOutputStream = PipedOutputStream()
        val pipedInputStream = PipedInputStream(pipedOutputStream)
        System.setIn(pipedInputStream)

        pipedOutputStream.write(input.toByteArray())

        val outputStreamForSystemOut = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStreamForSystemOut))

        val future = Executors.newSingleThreadExecutor().submit {
            controller.start()
        }

//        wait for all data to be read
        while (pipedInputStream.available() > 0) {
        }

        stopController()
        pipedInputStream.close()
        pipedOutputStream.close()

        val expectedOutput = """
            DY for DIVIDEND_YIELD
            PE for PE_RATIO
            TRADE for TRADE
            VWSP for VOLUME_WEIGHTED_STOCK_PRICE
            SI for SHARE_INDEX
            Q for QUIT
            H for HELP
            Successfully saved trade
            VWSP for TEA: 75
            Successfully saved trade
            VWSP for TEA: 83
            Successfully saved trade
            VWSP for TEA: 182
            Successfully saved trade
            VWSP for TEA: 182
            Successfully saved trade
            VWSP for TEA: 182
            VWSP for COF: 0
            Successfully saved trade
            VWSP for COF: 300
            Stock TEA, GeometricMean: 668.7403049764221
            Stock COF, GeometricMean: 1581.1388300841897
            Dividend Yield for COF: 8
            Dividend Yield for COF: 1
            Dividend Yield for MIL: 0
            PE Ratio for JUI: 5
            PE Ratio for JUI: 23
            PE Ratio for COF: 20
            Exiting
        """.trimIndent()

//        wait for controller to finish processing all data
        future.get()

        assertEquals(expectedOutput, outputWritten.joinToString("\n"))

    }

    private fun stopController() {
        val isRunning = controller::class.java.getDeclaredField("isRunning")
        isRunning.isAccessible = true
        isRunning.set(controller, false)
    }

}
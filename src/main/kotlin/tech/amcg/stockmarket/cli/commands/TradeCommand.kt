package tech.amcg.stockmarket.cli.commands

import tech.amcg.stockmarket.cli.commands.domain.VerifyResult
import tech.amcg.stockmarket.domain.Indicator
import tech.amcg.stockmarket.domain.StockIdentifier
import tech.amcg.stockmarket.domain.Trade
import tech.amcg.stockmarket.service.TradeService
import java.math.BigDecimal
import java.time.Instant
import java.time.format.DateTimeParseException

class TradeCommand(private val tradeService: TradeService) : Command {

    var stockIdentifier: StockIdentifier? = null
    var timestamp: Instant? = null
    var quantity: BigDecimal? = null
    var indicator: Indicator? = null
    var price: BigDecimal? = null
    override var validatedArguments = false

    override fun verifyArguments(arguments: List<String>): VerifyResult {
        if (arguments.size != 5) {
            return VerifyResult.fail("Expected 5 arguments, found ${arguments.size}")
        }
        try {
            stockIdentifier = StockIdentifier.valueOf(arguments.get(0))
        } catch (ex: IllegalArgumentException) {
            return VerifyResult.fail("Unknown stock identifier ${arguments.get(0)}")
        }
        try {
           timestamp = Instant.parse(arguments.get(1))
        } catch (ex: DateTimeParseException) {
            return VerifyResult.fail("Unparsable instant ${arguments.get(1)}")
        }
        try {
            quantity = BigDecimal(arguments.get(2))
        } catch (ex: NumberFormatException) {
            return VerifyResult.fail("Unparsable quantity ${arguments.get(2)}")
        }
        try {
            indicator = Indicator.valueOf(arguments.get(3))
        } catch (ex: IllegalArgumentException) {
            return VerifyResult.fail("Unparsable indicator ${arguments.get(3)}")
        }
        try {
            price = BigDecimal(arguments.get(4))
        } catch (ex: NumberFormatException) {
            return VerifyResult.fail("Unparsable price ${arguments.get(4)}")
        }
        validatedArguments = true
        return VerifyResult.success()
    }

    override fun execute(): String {
        return if (tradeService.saveTrade(Trade(stockIdentifier!!, timestamp!!, quantity!!, indicator!!, price!!))) {
            "Successfully saved trade"
        } else {
            "Failed to save trade"
        }
    }

}
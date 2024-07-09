package tech.amcg.stockmarket.cli.commands

import tech.amcg.stockmarket.cli.commands.domain.VerifyResult
import tech.amcg.stockmarket.domain.StockIdentifier
import tech.amcg.stockmarket.service.CalculationService
import java.time.Instant
import kotlin.time.Duration.Companion.minutes

class VolumeWeightedStockPriceCommand(private val calculationService: CalculationService) : Command {

    var stockIdentifier: StockIdentifier? = null

    override fun verifyArguments(arguments: List<String>): VerifyResult {
        if (arguments.size != 1) {
            return VerifyResult.fail("Expected 1 arguments, found ${arguments.size}")
        }
        try {
            stockIdentifier = StockIdentifier.valueOf(arguments.first())
        } catch (ex: IllegalArgumentException) {
            return VerifyResult.fail("Unknown stock identifier ${arguments.first()}")
        }
        validatedArguments = true
        return VerifyResult.success()
    }

    override fun execute(): String {
        return calculationService.volumeWeightedStockPrice(stockIdentifier!!, Instant.now().minusSeconds(15.minutes.inWholeSeconds)).let {
            "VWSP for $stockIdentifier: $it"
        }
    }

    override var validatedArguments: Boolean = false
}
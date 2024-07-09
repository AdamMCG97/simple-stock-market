package tech.amcg.stockmarket.cli.commands

import tech.amcg.stockmarket.cli.commands.domain.VerifyResult
import tech.amcg.stockmarket.datastore.share.ShareStore
import tech.amcg.stockmarket.domain.StockIdentifier
import java.math.BigDecimal

class DividendYieldCommand(private val shareStore: ShareStore) : Command {

    var stockIdentifier: StockIdentifier? = null
    var price: BigDecimal? = null
    override var validatedArguments = false


    override fun verifyArguments(arguments: List<String>): VerifyResult {
        if (arguments.size != 2) {
            return VerifyResult.fail("Expected 2 arguments, found ${arguments.size}")
        }
        try {
            stockIdentifier = StockIdentifier.valueOf(arguments.first())
        } catch (ex: IllegalArgumentException) {
            return VerifyResult.fail("Unknown stock identifier ${arguments.first()}")
        }
        try {
            price = BigDecimal(arguments.get(1))
        } catch (ex: NumberFormatException) {
            return VerifyResult.fail("Unparsable price ${arguments.get(1)}")
        }
        validatedArguments = true
        return VerifyResult.success()
    }

    override fun execute(): String {
        return shareStore.getShare(stockIdentifier!!)?.calculateDividendYield(price!!)?.let {
            "Dividend Yield for $stockIdentifier: $it"
        } ?: "Failed to calculate"
    }
}
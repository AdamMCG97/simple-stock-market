package tech.amcg.stockmarket.cli

import tech.amcg.stockmarket.cli.commands.Command
import tech.amcg.stockmarket.cli.commands.DividendYieldCommand
import tech.amcg.stockmarket.cli.commands.HelpCommand
import tech.amcg.stockmarket.cli.commands.PeRatioCommand
import tech.amcg.stockmarket.cli.commands.QuitCommand
import tech.amcg.stockmarket.cli.commands.ShareIndexCommand
import tech.amcg.stockmarket.cli.commands.TradeCommand
import tech.amcg.stockmarket.cli.commands.VolumeWeightedStockPriceCommand
import tech.amcg.stockmarket.cli.commands.domain.CliCommandDefinition
import tech.amcg.stockmarket.datastore.share.ShareStore
import tech.amcg.stockmarket.service.CalculationService
import tech.amcg.stockmarket.service.TradeService

class CommandFactory(private val calculationService: CalculationService, private val tradeService: TradeService, private val shareStore: ShareStore) {

    fun create(commandDefinition: CliCommandDefinition): Command {
        return when (commandDefinition) {
            CliCommandDefinition.DIVIDEND_YIELD -> DividendYieldCommand(shareStore)
            CliCommandDefinition.PE_RATIO -> PeRatioCommand(shareStore)
            CliCommandDefinition.TRADE -> TradeCommand(tradeService)
            CliCommandDefinition.VOLUME_WEIGHTED_STOCK_PRICE -> VolumeWeightedStockPriceCommand(calculationService)
            CliCommandDefinition.SHARE_INDEX -> ShareIndexCommand(calculationService)
            CliCommandDefinition.QUIT -> QuitCommand()
            CliCommandDefinition.HELP -> HelpCommand()
        }

    }

}
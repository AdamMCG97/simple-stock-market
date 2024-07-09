package tech.amcg.stockmarket

import tech.amcg.stockmarket.cli.CommandFactory
import tech.amcg.stockmarket.cli.Controller
import tech.amcg.stockmarket.datastore.share.SampleShareStore
import tech.amcg.stockmarket.datastore.trade.InMemoryTradeStore
import tech.amcg.stockmarket.service.CalculationService
import tech.amcg.stockmarket.service.TradeService

fun main(args: Array<String>) {

    val tradeStore = InMemoryTradeStore()
    val shareStore = SampleShareStore()

    val calculationService = CalculationService(tradeStore)
    val tradeService = TradeService(tradeStore)

    val commandFactory = CommandFactory(calculationService, tradeService, shareStore)

    val controller = Controller(commandFactory)

    controller.start()

}
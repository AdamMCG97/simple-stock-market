package tech.amcg.stockmarket.cli.commands

import tech.amcg.stockmarket.cli.commands.domain.VerifyResult
import tech.amcg.stockmarket.service.CalculationService

class ShareIndexCommand(private val calculationService: CalculationService) : Command {

    override var validatedArguments = true

    override fun verifyArguments(arguments: List<String>): VerifyResult {
        return VerifyResult.success()
    }

    override fun execute(): String {
        return calculationService.geometricMean().map { "Stock ${it.key}, GeometricMean: ${it.value}" }.joinToString("\n")
    }

}
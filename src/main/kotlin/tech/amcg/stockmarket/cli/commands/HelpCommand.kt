package tech.amcg.stockmarket.cli.commands

import tech.amcg.stockmarket.cli.commands.domain.CliCommandDefinition
import tech.amcg.stockmarket.cli.commands.domain.VerifyResult

class HelpCommand : Command {

    override var validatedArguments = true

    override fun verifyArguments(arguments: List<String>): VerifyResult {
        return VerifyResult.success()
    }

    override fun execute(): String {
        return CliCommandDefinition.entries.joinToString("\n") {
            "${it.argumentAlias} for ${it.name}"
        }
    }
}
package tech.amcg.stockmarket.cli.commands

import tech.amcg.stockmarket.cli.commands.domain.VerifyResult

class QuitCommand : Command {

    override var validatedArguments = true

    override fun verifyArguments(arguments: List<String>): VerifyResult {
        return VerifyResult.success()
    }

    override fun execute(): String {
        return "Exiting"
    }
}
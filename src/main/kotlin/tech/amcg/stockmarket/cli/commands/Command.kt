package tech.amcg.stockmarket.cli.commands

import tech.amcg.stockmarket.cli.commands.domain.VerifyResult

interface Command {

    fun verifyArguments(arguments: List<String>): VerifyResult

    fun execute(): String

    var validatedArguments: Boolean

    fun executeSafely(): String {
        return if (validatedArguments) {
           execute()
        } else {
            throw IllegalStateException("Cannot call execute on command without valid arguments")
        }
    }

}
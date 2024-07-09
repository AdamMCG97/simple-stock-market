package tech.amcg.stockmarket.cli

import tech.amcg.stockmarket.cli.commands.QuitCommand
import tech.amcg.stockmarket.cli.commands.domain.CliCommandDefinition
import tech.amcg.stockmarket.cli.commands.domain.ResultType

class Controller(private val commandFactory: CommandFactory, private val writeOutput: (String?) -> Unit = ::println) {

    private var isRunning = false

    fun start() {
        isRunning = true
        run()
    }

    private fun run() {
        while (isRunning) {
            val input = readln().split(" ")
            if (input.isEmpty()) {
                writeOutput("Please enter a command")
                continue
            }
            val commandDefinition = CliCommandDefinition.getByAlias(input.first())
            if (commandDefinition == null) {
                writeOutput("Unrecognised command")
                continue
            }
            val command = commandFactory.create(commandDefinition)
            val verifyArguments = command.verifyArguments(input.drop(1))
            if (verifyArguments.resultType == ResultType.FAIL) {
                writeOutput(verifyArguments.errorMessage)
                continue
            }
            writeOutput(command.executeSafely())
            if (command is QuitCommand) {
                isRunning = false
            }
        }
    }

}
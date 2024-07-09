package tech.amcg.stockmarket.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.amcg.stockmarket.cli.commands.domain.CliCommandDefinition

class CliCommandDefinitionTest {

    @Test
    fun `unique argument aliases`() {
        val duplicates = CliCommandDefinition.entries.groupBy { it.argumentAlias }.filter { it.value.size > 1 }
        assertEquals(0, duplicates.size, "Argument aliases not unique: ${duplicates.map { it.key }.joinToString()}")
    }

}
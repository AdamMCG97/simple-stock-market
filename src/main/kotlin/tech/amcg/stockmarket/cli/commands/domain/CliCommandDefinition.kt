package tech.amcg.stockmarket.cli.commands.domain

enum class CliCommandDefinition(val argumentAlias: String) {
    DIVIDEND_YIELD("DY") ,
    PE_RATIO("PE"),
    TRADE("TRADE"),
    VOLUME_WEIGHTED_STOCK_PRICE("VWSP"),
    SHARE_INDEX("SI"),
    QUIT("Q"),
    HELP("H");

    companion object {
       fun getByAlias(alias: String): CliCommandDefinition? {
           return entries.firstOrNull { it.argumentAlias.uppercase() == alias.uppercase() }
       }
    }

}
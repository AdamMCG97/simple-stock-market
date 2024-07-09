package tech.amcg.stockmarket.datastore.share

import tech.amcg.stockmarket.domain.OrdinaryShare
import tech.amcg.stockmarket.domain.PreferredShare
import tech.amcg.stockmarket.domain.Share
import tech.amcg.stockmarket.domain.StockIdentifier
import java.math.BigDecimal

class SampleShareStore : ShareStore {

    private val shares = listOf(
        OrdinaryShare(StockIdentifier.TEA, BigDecimal.valueOf(0), BigDecimal.valueOf(150)),
        PreferredShare(StockIdentifier.COF, BigDecimal.valueOf(8), BigDecimal.valueOf(100), BigDecimal.valueOf(4)),
        OrdinaryShare(StockIdentifier.MIL, BigDecimal.valueOf(8), BigDecimal.valueOf(100)),
        OrdinaryShare(StockIdentifier.JUI, BigDecimal.valueOf(23), BigDecimal.valueOf(70)),
        OrdinaryShare(StockIdentifier.WAT, BigDecimal.valueOf(13), BigDecimal.valueOf(250)),
    ).associateBy { it.stockIdentifier }

    override fun getShare(stockIdentifier: StockIdentifier): Share? {
        return shares.get(stockIdentifier)
    }


}
package tech.amcg.stockmarket.datastore.share

import tech.amcg.stockmarket.domain.Share
import tech.amcg.stockmarket.domain.StockIdentifier

interface ShareStore {

    fun getShare(stockIdentifier: StockIdentifier): Share?

}
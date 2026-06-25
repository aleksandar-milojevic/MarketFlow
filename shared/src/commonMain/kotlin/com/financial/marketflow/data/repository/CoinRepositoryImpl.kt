package com.financial.marketflow.data.repository

import com.financial.marketflow.data.model.CoinDetail
import com.financial.marketflow.data.model.CoinMarket
import com.financial.marketflow.data.network.CoinGeckoApi

class CoinRepositoryImpl(private val api: CoinGeckoApi) : CoinRepository {

    override suspend fun getTopCoins(vsCurrency: String): Result<List<CoinMarket>> {
        return runCatching {
            api.getCoinsMarkets(vsCurrency = vsCurrency)
        }
    }

    override suspend fun getCoinDetail(id: String): Result<CoinDetail> {
        return runCatching {
            api.getCoinDetail(id)
        }
    }
}

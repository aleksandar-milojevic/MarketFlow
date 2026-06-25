package com.financial.marketflow.data.repository

import com.financial.marketflow.data.model.CoinDetail
import com.financial.marketflow.data.model.CoinMarket

interface CoinRepository {
    suspend fun getTopCoins(vsCurrency: String = "usd"): Result<List<CoinMarket>>
    suspend fun getCoinDetail(id: String): Result<CoinDetail>
}

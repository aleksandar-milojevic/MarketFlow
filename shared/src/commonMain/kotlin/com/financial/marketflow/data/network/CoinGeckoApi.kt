package com.financial.marketflow.data.network

import com.financial.marketflow.BuildKonfig
import com.financial.marketflow.data.model.CoinDetail
import com.financial.marketflow.data.model.CoinMarket
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CoinGeckoApi {

    private val baseUrl = "https://api.coingecko.com/api/v3"

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    suspend fun getCoinsMarkets(
        vsCurrency: String = "usd",
    ): List<CoinMarket> {
        return client.get("$baseUrl/coins/markets") {
            header("x-cg-demo-api-key", BuildKonfig.COINGECKO_API_KEY)
            parameter("vs_currency", vsCurrency)
        }.body()
    }

    suspend fun getCoinDetail(id: String): CoinDetail {
        return client.get("$baseUrl/coins/$id") {
            header("x-cg-demo-api-key", BuildKonfig.COINGECKO_API_KEY)
        }.body()
    }

    fun close() = client.close()
}

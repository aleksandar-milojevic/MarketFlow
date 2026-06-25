package com.financial.marketflow.presentation

data class CoinUiState(
    val coins: List<CoinItemState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class CoinItemState(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int?,
    val price: Double,
    val currency: String = "USD",
    val priceChangePercentage24h: Double?
)

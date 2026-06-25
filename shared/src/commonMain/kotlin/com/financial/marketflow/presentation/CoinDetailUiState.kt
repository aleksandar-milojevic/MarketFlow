package com.financial.marketflow.presentation

data class CoinDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val id: String = "",
    val name: String = "",
    val symbol: String = "",
    val imageUrl: String = "",
    val rank: Int? = null,
    val currentPriceUsd: Double? = null,
    val priceChangePercentage24h: Double? = null,
    val marketCapUsd: Double? = null,
    val totalVolumeUsd: Double? = null,
    val athUsd: Double? = null,
    val athChangePercentage: Double? = null,
    val atlUsd: Double? = null,
    val circulatingSupply: Double? = null,
    val totalSupply: Double? = null,
    val maxSupply: Double? = null,
    val description: String? = null,
    val categories: List<String> = emptyList(),
    val homepage: String? = null,
    val twitterHandle: String? = null,
    val redditUrl: String? = null
)

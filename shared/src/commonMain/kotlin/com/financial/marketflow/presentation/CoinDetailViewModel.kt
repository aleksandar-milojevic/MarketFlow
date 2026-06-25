package com.financial.marketflow.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financial.marketflow.data.repository.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinDetailViewModel(
    private val repository: CoinRepository,
    private val coinId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(CoinDetailUiState())
    val uiState: StateFlow<CoinDetailUiState> = _uiState.asStateFlow()

    init {
        fetchDetail()
    }

    private fun fetchDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getCoinDetail(coinId).fold(
                onSuccess = { detail ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            id = detail.id,
                            name = detail.name,
                            symbol = detail.symbol.uppercase(),
                            imageUrl = detail.image?.large ?: "",
                            rank = detail.marketCapRank,
                            currentPriceUsd = detail.marketData?.currentPrice?.usd,
                            priceChangePercentage24h = detail.marketData?.priceChangePercentage24h,
                            marketCapUsd = detail.marketData?.marketCap?.usd,
                            totalVolumeUsd = detail.marketData?.totalVolume?.usd,
                            athUsd = detail.marketData?.ath?.usd,
                            athChangePercentage = detail.marketData?.athChangePercentage?.usd,
                            atlUsd = detail.marketData?.atl?.usd,
                            circulatingSupply = detail.marketData?.circulatingSupply,
                            totalSupply = detail.marketData?.totalSupply,
                            maxSupply = detail.marketData?.maxSupply,
                            description = detail.description?.en?.take(300),
                            categories = detail.categories,
                            homepage = detail.links?.homepage?.firstOrNull { it.isNotEmpty() },
                            twitterHandle = detail.links?.twitterScreenName,
                            redditUrl = detail.links?.subredditUrl
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.message) }
                }
            )
        }
    }
}

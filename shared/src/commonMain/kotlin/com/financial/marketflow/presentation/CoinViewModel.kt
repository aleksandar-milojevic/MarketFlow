package com.financial.marketflow.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financial.marketflow.data.repository.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinViewModel(private val repository: CoinRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CoinUiState())
    val uiState: StateFlow<CoinUiState> = _uiState.asStateFlow()

    init {
        fetchCoins()
    }

    private fun fetchCoins() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getTopCoins().fold(
                onSuccess = { result ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            coins = result.map { coin ->
                                CoinItemState(
                                    id = coin.id,
                                    name = coin.name,
                                    symbol = coin.symbol,
                                    rank = coin.marketCapRank,
                                    price = coin.currentPrice ?: 0.0,
                                    priceChangePercentage24h = coin.priceChangePercentage24h
                                )
                            }
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

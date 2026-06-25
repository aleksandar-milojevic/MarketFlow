package com.financial.marketflow.di

import com.financial.marketflow.data.network.CoinGeckoApi
import com.financial.marketflow.data.repository.CoinRepository
import com.financial.marketflow.data.repository.CoinRepositoryImpl
import com.financial.marketflow.presentation.CoinDetailViewModel
import com.financial.marketflow.presentation.CoinViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::CoinGeckoApi)
    singleOf(::CoinRepositoryImpl) bind CoinRepository::class
    viewModelOf(::CoinViewModel)
    viewModel { (coinId: String) -> CoinDetailViewModel(get(), coinId) }
}

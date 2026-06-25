package com.financial.marketflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.financial.marketflow.presentation.screen.CoinDetailScreen
import com.financial.marketflow.presentation.screen.CoinListScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.CoinList
    ) {
        composable<Screen.CoinList> {
            CoinListScreen(
                onCoinClick = { coinId ->
                    navController.navigate(Screen.CoinDetail(coinId))
                }
            )
        }
        composable<Screen.CoinDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.CoinDetail>()
            CoinDetailScreen(
                coinId = route.coinId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

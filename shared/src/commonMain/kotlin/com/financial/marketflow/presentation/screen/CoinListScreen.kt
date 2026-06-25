package com.financial.marketflow.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.financial.marketflow.presentation.CoinItemState
import com.financial.marketflow.presentation.CoinViewModel
import org.koin.compose.viewmodel.koinViewModel

private val BackgroundColor = Color(0xFFF5F7FA)
private val CardColor = Color.White
private val PrimaryText = Color(0xFF1C1C1E)
private val SecondaryText = Color(0xFF8E8E93)
private val GreenColor = Color(0xFF34C759)
private val RedColor = Color(0xFFFF3B30)

@Composable
fun CoinListScreen(onCoinClick: (String) -> Unit) {
    val viewModel = koinViewModel<CoinViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 3 }
    }

    Scaffold(
        containerColor = BackgroundColor,
        contentWindowInsets = WindowInsets.safeDrawing,
        floatingActionButton = {
            if (showScrollToTop) {
                SmallFloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    containerColor = Color(0xFF007AFF),
                    contentColor = Color.White
                ) {
                    Text("up", fontSize = 12.sp)
                }
            }
        }
    ) { innerPadding ->
        when {
            uiState.isLoading -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF007AFF))
            }
            uiState.error != null -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${uiState.error}", color = RedColor)
            }
            else -> LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = innerPadding + PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = "Market",
                        color = PrimaryText,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                items(uiState.coins) { coin ->
                    CoinCard(coin = coin, onClick = { onCoinClick(coin.id) })
                }
            }
        }
    }
}

@Composable
private fun CoinCard(coin: CoinItemState, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${coin.rank}",
                    color = SecondaryText,
                    fontSize = 12.sp,
                    modifier = Modifier.width(28.dp)
                )
                Column {
                    Text(
                        text = coin.name,
                        color = PrimaryText,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = coin.symbol.uppercase(),
                        color = SecondaryText,
                        fontSize = 12.sp
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$${formatPrice(coin.price)}",
                    color = PrimaryText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                val change = coin.priceChangePercentage24h
                if (change != null) {
                    val positive = change >= 0
                    Text(
                        text = "${if (positive) "+" else ""}${formatDecimal(change)}%",
                        color = if (positive) GreenColor else RedColor,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

private fun formatPrice(price: Double): String = when {
    price >= 1000 -> price.toLong().toString()
    price >= 1 -> formatDecimal(price, 2)
    else -> formatDecimal(price, 6)
}

private fun formatDecimal(value: Double, decimals: Int = 2): String {
    val factor = 10.0.pow(decimals)
    val rounded = kotlin.math.round(value * factor)
    val intPart = (rounded / factor).toLong()
    val fracPart = (rounded % factor).toLong()
    return "$intPart.${fracPart.toString().padStart(decimals, '0')}"
}

private fun Double.pow(n: Int): Double {
    var result = 1.0
    repeat(n) { result *= this }
    return result
}

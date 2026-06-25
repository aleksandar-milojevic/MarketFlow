package com.financial.marketflow.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.financial.marketflow.presentation.CoinDetailViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

private val BackgroundColor = Color(0xFFF5F7FA)
private val CardColor = Color.White
private val PrimaryText = Color(0xFF1C1C1E)
private val SecondaryText = Color(0xFF8E8E93)
private val GreenColor = Color(0xFF34C759)
private val RedColor = Color(0xFFFF3B30)
private val DividerColor = Color(0xFFE5E5EA)
private val AccentColor = Color(0xFF007AFF)
private val TagBgColor = Color(0xFFEEF2FF)
private val TagTextColor = Color(0xFF3B5BDB)

@Composable
fun CoinDetailScreen(coinId: String, onBack: () -> Unit) {
    val viewModel = koinViewModel<CoinDetailViewModel> { parametersOf(coinId) }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = BackgroundColor,
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        when {
            uiState.isLoading -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AccentColor)
            }
            uiState.error != null -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${uiState.error}", color = RedColor)
            }
            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextButton(onClick = onBack) {
                    Text("Back", color = AccentColor)
                }

                Column {
                    Text(
                        text = uiState.name,
                        color = PrimaryText,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${uiState.symbol} - Rank #${uiState.rank}",
                        color = SecondaryText,
                        fontSize = 14.sp
                    )
                }

                val change = uiState.priceChangePercentage24h
                val positive = (change ?: 0.0) >= 0
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "$${uiState.currentPriceUsd?.let { formatPrice(it) } ?: "-"}",
                        color = PrimaryText,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (change != null) {
                        Text(
                            text = "${if (positive) "+" else ""}${formatDecimal(change)}%",
                            color = if (positive) GreenColor else RedColor,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }

                HorizontalDivider(color = DividerColor)

                DetailCard {
                    DetailRow("Market Cap", uiState.marketCapUsd?.let { "$${formatLarge(it)}" } ?: "-")
                    DetailRow("24h Volume", uiState.totalVolumeUsd?.let { "$${formatLarge(it)}" } ?: "-")
                    DetailRow(
                        label = "ATH",
                        value = uiState.athUsd?.let { "$${formatPrice(it)}" } ?: "-",
                        suffix = uiState.athChangePercentage?.let { "${formatDecimal(it)}%" },
                        suffixColor = RedColor
                    )
                    DetailRow("ATL", uiState.atlUsd?.let { "$${formatPrice(it)}" } ?: "-")
                }

                DetailCard {
                    DetailRow("Circulating Supply", uiState.circulatingSupply?.let { formatLarge(it) } ?: "-")
                    DetailRow("Total Supply", uiState.totalSupply?.let { formatLarge(it) } ?: "-")
                    DetailRow("Max Supply", uiState.maxSupply?.let { formatLarge(it) } ?: "No limit")
                }

                if (uiState.categories.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        uiState.categories.take(3).forEach { category ->
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = TagBgColor
                            ) {
                                Text(
                                    text = category,
                                    color = TagTextColor,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                if (!uiState.description.isNullOrBlank()) {
                    DetailCard {
                        Text(
                            text = "About",
                            color = PrimaryText,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Text(
                            text = uiState.description ?: "",
                            color = SecondaryText,
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    suffix: String? = null,
    suffixColor: Color = SecondaryText
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, color = SecondaryText, fontSize = 13.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = value, color = PrimaryText, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            if (suffix != null) Text(text = suffix, color = suffixColor, fontSize = 13.sp)
        }
    }
}

private fun formatPrice(price: Double): String = when {
    price >= 1000 -> price.toLong().toString()
    price >= 1 -> roundDecimal(price, 2)
    else -> roundDecimal(price, 6)
}

private fun formatDecimal(value: Double): String = roundDecimal(value, 2)

private fun formatLarge(value: Double): String = when {
    value >= 1_000_000_000 -> "${roundDecimal(value / 1_000_000_000, 2)}B"
    value >= 1_000_000 -> "${roundDecimal(value / 1_000_000, 2)}M"
    value >= 1_000 -> "${roundDecimal(value / 1_000, 2)}K"
    else -> roundDecimal(value, 2)
}

private fun roundDecimal(value: Double, decimals: Int): String {
    val factor = pow10(decimals)
    val rounded = kotlin.math.round(value * factor)
    val intPart = (rounded / factor).toLong()
    val fracPart = kotlin.math.abs(rounded % factor).toLong()
    return "$intPart.${fracPart.toString().padStart(decimals, '0')}"
}

private fun pow10(n: Int): Double {
    var result = 1.0
    repeat(n) { result *= 10.0 }
    return result
}

package com.financial.marketflow

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.financial.marketflow.navigation.NavGraph

@Composable
fun App() {
    MaterialTheme {
        NavGraph()
    }
}

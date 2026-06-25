package com.financial.marketflow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
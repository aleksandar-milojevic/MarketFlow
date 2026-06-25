package com.financial.marketflow.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetail(
    val id: String,
    val symbol: String,
    val name: String,
    @SerialName("web_slug") val webSlug: String? = null,
    val categories: List<String> = emptyList(),
    val description: Description? = null,
    val links: Links? = null,
    val image: CoinImage? = null,
    @SerialName("genesis_date") val genesisDate: String? = null,
    @SerialName("sentiment_votes_up_percentage") val sentimentVotesUpPercentage: Double? = null,
    @SerialName("sentiment_votes_down_percentage") val sentimentVotesDownPercentage: Double? = null,
    @SerialName("watchlist_portfolio_users") val watchlistPortfolioUsers: Int? = null,
    @SerialName("market_cap_rank") val marketCapRank: Int? = null,
    @SerialName("last_updated") val lastUpdated: String? = null,
    @SerialName("market_data") val marketData: MarketData? = null,
    @SerialName("community_data") val communityData: CommunityData? = null,
    @SerialName("developer_data") val developerData: DeveloperData? = null,
    val tickers: List<Ticker> = emptyList()
)

@Serializable
data class Description(
    val en: String? = null
)

@Serializable
data class CoinImage(
    val thumb: String? = null,
    val small: String? = null,
    val large: String? = null
)

@Serializable
data class Links(
    val homepage: List<String> = emptyList(),
    val whitepaper: String? = null,
    @SerialName("blockchain_site") val blockchainSite: List<String> = emptyList(),
    @SerialName("twitter_screen_name") val twitterScreenName: String? = null,
    @SerialName("subreddit_url") val subredditUrl: String? = null,
    @SerialName("repos_url") val reposUrl: ReposUrl? = null
)

@Serializable
data class ReposUrl(
    val github: List<String> = emptyList(),
    val bitbucket: List<String> = emptyList()
)

@Serializable
data class MarketData(
    @SerialName("current_price") val currentPrice: CurrencyUsd? = null,
    val ath: CurrencyUsd? = null,
    @SerialName("ath_change_percentage") val athChangePercentage: CurrencyUsd? = null,
    @SerialName("ath_date") val athDate: CurrencyDateUsd? = null,
    val atl: CurrencyUsd? = null,
    @SerialName("atl_change_percentage") val atlChangePercentage: CurrencyUsd? = null,
    @SerialName("atl_date") val atlDate: CurrencyDateUsd? = null,
    @SerialName("market_cap") val marketCap: CurrencyUsd? = null,
    @SerialName("market_cap_rank") val marketCapRank: Int? = null,
    @SerialName("total_volume") val totalVolume: CurrencyUsd? = null,
    @SerialName("high_24h") val high24h: CurrencyUsd? = null,
    @SerialName("low_24h") val low24h: CurrencyUsd? = null,
    @SerialName("price_change_24h") val priceChange24h: Double? = null,
    @SerialName("price_change_percentage_24h") val priceChangePercentage24h: Double? = null,
    @SerialName("price_change_percentage_7d") val priceChangePercentage7d: Double? = null,
    @SerialName("price_change_percentage_14d") val priceChangePercentage14d: Double? = null,
    @SerialName("price_change_percentage_30d") val priceChangePercentage30d: Double? = null,
    @SerialName("price_change_percentage_1y") val priceChangePercentage1y: Double? = null,
    @SerialName("total_supply") val totalSupply: Double? = null,
    @SerialName("max_supply") val maxSupply: Double? = null,
    @SerialName("circulating_supply") val circulatingSupply: Double? = null,
    @SerialName("last_updated") val lastUpdated: String? = null
)

@Serializable
data class CurrencyUsd(
    val usd: Double? = null
)

@Serializable
data class CurrencyDateUsd(
    val usd: String? = null
)

@Serializable
data class CommunityData(
    @SerialName("reddit_subscribers") val redditSubscribers: Int? = null,
    @SerialName("reddit_accounts_active_48h") val redditAccountsActive48h: Int? = null,
    @SerialName("reddit_average_posts_48h") val redditAveragePosts48h: Double? = null,
    @SerialName("reddit_average_comments_48h") val redditAverageComments48h: Double? = null,
    @SerialName("telegram_channel_user_count") val telegramChannelUserCount: Int? = null
)

@Serializable
data class DeveloperData(
    val forks: Int? = null,
    val stars: Int? = null,
    val subscribers: Int? = null,
    @SerialName("total_issues") val totalIssues: Int? = null,
    @SerialName("closed_issues") val closedIssues: Int? = null,
    @SerialName("pull_requests_merged") val pullRequestsMerged: Int? = null,
    @SerialName("commit_count_4_weeks") val commitCount4Weeks: Int? = null
)

@Serializable
data class Ticker(
    val base: String,
    val target: String,
    val market: TickerMarket? = null,
    val last: Double? = null,
    val volume: Double? = null,
    @SerialName("trust_score") val trustScore: String? = null,
    @SerialName("trade_url") val tradeUrl: String? = null,
    @SerialName("coin_id") val coinId: String? = null,
    @SerialName("is_anomaly") val isAnomaly: Boolean = false,
    @SerialName("is_stale") val isStale: Boolean = false
)

@Serializable
data class TickerMarket(
    val name: String? = null,
    val identifier: String? = null
)

package dev.coinroutine.app.portfolio.domain

import dev.coinroutine.app.core.domain.coin.Coin

data class PortfolioCoinModel(
    val coin: Coin,
    val performancePercent: Double,     // 收益率 = (当前价 - 平均买入价) / 平均买入价 * 100
    val averagePurchasePrice: Double,   // 平均买入价 = 买入总市值 / 持有数量
    val ownedAmountInUnit: Double,      // 持有数量
    val ownedAmountInFiat: Double,      // 当前总市值
)

package dev.coinroutine.app.portfolio.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PortfolioCoin")
data class PortfolioCoinEntity(
    @PrimaryKey val coinId: String,
    val name: String,                   // 币种名称
    val symbol: String,                 // 币种符号
    val iconUrl: String,                // 币种图标
    val averagePurchasePrice: Double,   // 买入平均价格
    val amountOwned: Double,            // 持有数量
    val timestamp: Long,                // 时间戳
)
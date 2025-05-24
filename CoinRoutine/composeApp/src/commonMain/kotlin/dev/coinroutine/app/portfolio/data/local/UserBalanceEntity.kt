package dev.coinroutine.app.portfolio.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserBalance")
data class UserBalanceEntity(
    @PrimaryKey val id: Int = 1,
    val cashBalance: Double,     // 现金余额
)
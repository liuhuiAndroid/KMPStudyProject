package dev.coinroutine.app.portfolio.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioDao {

    // @Upsert 是 Room 的新特性，表示：如果主键已存在：更新这条记录；如果主键不存在：插入新记录；
    @Upsert
    suspend fun insert(portfolioCoinEntity: PortfolioCoinEntity)

    @Query("SELECT * FROM PortfolioCoin ORDER BY timestamp DESC")
    fun getAllOwnedCoins(): Flow<List<PortfolioCoinEntity>>

    @Query("SELECT * FROM PortfolioCoin WHERE coinId = :coinId")
    suspend fun getCoinById(coinId: String): PortfolioCoinEntity?

    @Query("DELETE FROM PortfolioCoin WHERE coinId = :coinId")
    suspend fun deletePortfolioItem(coinId: String)
}
package dev.coinroutine.app.portfolio.domain

import dev.coinroutine.app.core.domain.DataError
import dev.coinroutine.app.core.domain.EmptyResult
import dev.coinroutine.app.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {

    // 初始化用户资产
    suspend fun initializeBalance()
    // 所有持仓币种
    fun allPortfolioCoinsFlow(): Flow<Result<List<PortfolioCoinModel>, DataError.Remote>>
    // 获取某个币的持仓信息
    suspend fun getPortfolioCoin(coinId: String): Result<PortfolioCoinModel?, DataError.Remote>
    // 更新某个持仓币信息
    suspend fun savePortfolioCoin(portfolioCoin: PortfolioCoinModel): EmptyResult<DataError.Local>
    // 删除某个币的持仓信息
    suspend fun removeCoinFromPortfolio(coinId: String)

    // 总资产估值计算
    fun calculateTotalPortfolioValue(): Flow<Result<Double, DataError.Remote>>
    // 总资产余额流
    fun totalBalanceFlow(): Flow<Result<Double, DataError.Remote>>
    // 现金余额流
    fun cashBalanceFlow(): Flow<Double>
    // 更新现金余额
    suspend fun updateCashBalance(newBalance: Double)
}
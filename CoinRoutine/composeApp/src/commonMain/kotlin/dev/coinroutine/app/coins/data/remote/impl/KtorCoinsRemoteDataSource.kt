package dev.coinroutine.app.coins.data.remote.impl

import dev.coinroutine.app.coins.data.remote.dto.CoinDetailsResponseDto
import dev.coinroutine.app.coins.data.remote.dto.CoinPriceHistoryResponseDto
import dev.coinroutine.app.coins.data.remote.dto.CoinsResponseDto
import dev.coinroutine.app.coins.domain.api.CoinsRemoteDataSource
import dev.coinroutine.app.core.domain.DataError
import dev.coinroutine.app.core.domain.Result
import dev.coinroutine.app.core.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val BASE_URL = "https://api.coinranking.com/v2"

class KtorCoinsRemoteDataSource(
    private val httpClient: HttpClient
) : CoinsRemoteDataSource {

    override suspend fun getListOfCoins(): Result<CoinsResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coins")
        }
    }

    override suspend fun getPriceHistory(coinId: String): Result<CoinPriceHistoryResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coin/$coinId/history")
        }
    }

    override suspend fun getCoinById(coinId: String): Result<CoinDetailsResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/coin/$coinId")
        }
    }
}
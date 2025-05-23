package dev.coinroutine.app.core.domain.coin

data class Coin(
    val id: String,         // 币种的唯一标识符
    val name: String,       // 币种的全名
    val symbol: String,     // 币种的简称
    val iconUrl: String,    // 币种的图标
)
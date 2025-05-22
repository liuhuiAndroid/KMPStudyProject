package dev.coinroutine.app.core.database.portfolio

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import dev.coinroutine.app.portfolio.data.local.PortfolioCoinEntity
import dev.coinroutine.app.portfolio.data.local.PortfolioDao
import dev.coinroutine.app.portfolio.data.local.UserBalanceDao
import dev.coinroutine.app.portfolio.data.local.UserBalanceEntity

@ConstructedBy(PortfolioDatabaseCreator::class)
@Database(entities = [PortfolioCoinEntity::class, UserBalanceEntity::class], version = 2)
abstract class PortfolioDatabase: RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
    abstract fun userBalanceDao(): UserBalanceDao
}
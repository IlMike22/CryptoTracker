package de.mindmarket.cryptotracker.crypto.domain

import de.mindmarket.cryptotracker.core.domain.util.NetworkError
import de.mindmarket.cryptotracker.core.domain.util.Result
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinsHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}
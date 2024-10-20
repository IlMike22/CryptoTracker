package de.mindmarket.cryptotracker.crypto.domain

import de.mindmarket.cryptotracker.core.domain.util.NetworkError
import de.mindmarket.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}
package de.mindmarket.cryptotracker.crypto.data.network

import de.mindmarket.cryptotracker.core.data.network.constructUrl
import de.mindmarket.cryptotracker.core.data.network.safeCall
import de.mindmarket.cryptotracker.core.domain.util.NetworkError
import de.mindmarket.cryptotracker.core.domain.util.Result
import de.mindmarket.cryptotracker.core.domain.util.map
import de.mindmarket.cryptotracker.crypto.data.mappers.toCoin
import de.mindmarket.cryptotracker.crypto.data.network.dto.CoinsResponseDto
import de.mindmarket.cryptotracker.crypto.domain.Coin
import de.mindmarket.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}
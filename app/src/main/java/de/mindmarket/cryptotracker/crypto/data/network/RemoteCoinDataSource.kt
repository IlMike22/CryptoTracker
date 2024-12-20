package de.mindmarket.cryptotracker.crypto.data.network

import de.mindmarket.cryptotracker.core.data.network.constructUrl
import de.mindmarket.cryptotracker.core.data.network.safeCall
import de.mindmarket.cryptotracker.core.domain.util.NetworkError
import de.mindmarket.cryptotracker.core.domain.util.Result
import de.mindmarket.cryptotracker.core.domain.util.map
import de.mindmarket.cryptotracker.crypto.data.mappers.toCoin
import de.mindmarket.cryptotracker.crypto.data.mappers.toCoinPrice
import de.mindmarket.cryptotracker.crypto.data.network.dto.CoinHistoryDto
import de.mindmarket.cryptotracker.crypto.data.network.dto.CoinsResponseDto
import de.mindmarket.cryptotracker.crypto.domain.Coin
import de.mindmarket.cryptotracker.crypto.domain.CoinDataSource
import de.mindmarket.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override suspend fun getCoinsHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}
package de.mindmarket.cryptotracker.crypto.presentation.coin_list

import de.mindmarket.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}
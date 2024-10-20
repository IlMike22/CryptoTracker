package de.mindmarket.cryptotracker.crypto.presentation.coin_list

import de.mindmarket.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
}
package de.mindmarket.cryptotracker.crypto.presentation.coin_list

import CryptoTrackerTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import de.mindmarket.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import de.mindmarket.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import de.mindmarket.cryptotracker.crypto.presentation.models.CoinUi
import de.mindmarket.cryptotracker.crypto.presentation.models.toCoinUi

@Composable
fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.coins) { coinUi ->
                CoinListItem(
                    coinUi = coinUi,
                    onClick = {
                        onAction(CoinListAction.OnCoinClick(coinUi))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            state = CoinListState(
                isLoading = false,
                coins = (1..100).map {
                    previewCoin.copy(id = it.toString())
                }
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction  = {}
        )
    }
}
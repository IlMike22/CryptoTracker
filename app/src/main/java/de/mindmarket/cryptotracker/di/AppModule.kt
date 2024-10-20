package de.mindmarket.cryptotracker.di

import de.mindmarket.cryptotracker.core.data.network.HttpClientFactory
import de.mindmarket.cryptotracker.crypto.data.network.RemoteCoinDataSource
import de.mindmarket.cryptotracker.crypto.domain.CoinDataSource
import de.mindmarket.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)

}
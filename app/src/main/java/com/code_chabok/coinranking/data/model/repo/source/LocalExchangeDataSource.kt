package com.code_chabok.coinranking.data.model.repo.source

import com.code_chabok.coinranking.data.model.Crypto
import com.code_chabok.coinranking.data.model.Exchange


class LocalExchangeDataSource: ExchangeDataSource {

    override suspend fun getAllExchange():List<Exchange> {
        return arrayListOf(
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false),
            Exchange("1","Binance","https:example.com","23.20 billion",false)
        )
    }

    override fun insertList(list: List<Exchange>) {

    }
}
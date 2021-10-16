package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.code_chabok.coinranking.data.model.dataClass.serverModel.exchangeListResource.ExchangeListModel
import com.google.gson.annotations.SerializedName


@Entity
data class Exchange(
    @SerializedName("24hVolume")
    val hVolume: Double,
    val marketShare: String,
    val iconUrl: String,
    val name: String,
    val rank: Int,
    val numberOfMarkets: String,
    val numberOfCoins: String,
    @PrimaryKey val uuid: String,
    val description: String?= null,
    var isExpanded: Boolean = false,
    ) {
    fun convertExchangeListModel(): ExchangeListModel {
        return ExchangeListModel(
            hVolume =hVolume,
            iconUrl =iconUrl,
            name = name,
            uuid = uuid,
            rank = rank,
            numberOfMarkets = numberOfMarkets,
            numberOfCoins = numberOfCoins,
            marketShare = marketShare
        )
    }
}

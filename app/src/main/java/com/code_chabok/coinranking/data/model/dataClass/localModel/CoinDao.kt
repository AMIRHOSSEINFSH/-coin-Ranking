package com.code_chabok.coinranking.data.model.dataClass.localModel

import androidx.lifecycle.LiveData
import androidx.room.*
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.localModel.relation.CoinAndBookmark
import com.code_chabok.coinranking.data.model.dataClass.searchModel.SearchCoin

@Dao
interface CoinDao {

    @Query("UPDATE coin SET iconUrl=:iconUrl,name=:name WHERE uuid=:uuid")
    fun updateSearchCoin(uuid: String,iconUrl: String,name: String)

    @Query("SELECT * FROM coin WHERE name LIKE '%'|| :query || '%' ")
    fun getSearchedCoins(query: String): List<CoinAndBookmark>

    @Transaction
    @Query("SELECT * FROM coin")
    fun getCoinsAndBookMarks(): LiveData<List<CoinAndBookmark>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetCoins(coinList: List<Coin>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: Coin)

    @Update
    fun updateCoin(coin: Coin)

    @Query("SELECT * FROM coin")
    fun getCoins(): LiveData<List<Coin>>

    @Transaction
    @Query("SELECT * FROM coin ORDER BY CASE WHEN :isDesc = 1 THEN price END DESC , CASE WHEN :isDesc = 0 THEN price END ASC")
    fun getPriceOrdered(isDesc: Boolean): List<CoinAndBookmark>

    @Transaction
    @Query("SELECT * FROM coin ORDER BY CASE WHEN :isDesc = 1 THEN marketCap END DESC , CASE WHEN :isDesc = 0 THEN marketCap END ASC")
    fun getMarketCapOrdered(isDesc: Boolean): List<CoinAndBookmark>

    @Query("SELECT * FROM coin WHERE uuid =:uuid")
    fun getDetailedCoin(uuid: String): LiveData<CoinAndBookmark>



}
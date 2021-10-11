package com.code_chabok.coinranking.domain

import androidx.lifecycle.LiveData
import com.code_chabok.coinranking.common.Resource
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel
import com.code_chabok.coinranking.data.model.repo.CoinListRepository
import com.code_chabok.coinranking.feature.home.HomeViewModel
import javax.inject.Inject

class getSortedList @Inject constructor(val repo: CoinListRepository) {

    suspend operator fun invoke(type: HomeViewModel.SortType): List<CoinListModel> =
        repo.getSortedList(type)


}
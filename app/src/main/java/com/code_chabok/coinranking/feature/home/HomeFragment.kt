package com.code_chabok.coinranking.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.code_chabok.coinranking.R
import com.code_chabok.coinranking.common.BaseCoinAdapter
import com.code_chabok.coinranking.common.CoinFragment
import com.code_chabok.coinranking.data.model.dataClass.CoinDetail
import com.code_chabok.coinranking.data.model.dataClass.CoinListModel

import com.code_chabok.coinranking.databinding.FragmentHomeBinding
import com.code_chabok.coinranking.domain.getCoinDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : CoinFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val bining: FragmentHomeBinding? get() = _binding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: BaseCoinAdapter
    private val timeList = arrayListOf("24h", "7h", "30d")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        rootView = bining?.root as CoordinatorLayout

        return bining!!.root
    }

    private var isDetail = false
    fun isInDetail(boolean: Boolean): HomeFragment {
        isDetail = boolean
        return this
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setShimmerIndicator(true, HomeShimmer = true)
        setUpSpinners()
        viewModel.errorLiveData.observe(viewLifecycleOwner){ isError ->
            if (isError)
                showSnackBar("List is Empty !")
        }
        if (!isDetail) {
            //viewModel.backStackDetecter.value = this
        }
        adapter = BaseCoinAdapter(onUpdateClickListener = { uuid: String, isBookmark: Boolean,_: Int ->
            viewModel.updateNewBookmark(uuid, isBookmark)
        },
            onItemClickListener = { coinListModel ->
                viewModel.getSpcificCoinDetail(coinListModel.uuid)
                viewModel.coinDetailObserver
            })

        adapter.setActivity(requireActivity())
        adapter.apply {
            setActivity(requireActivity())
            setIsDetail(isDetail)
            showBubble = savedInstanceState == null
        }
        bining?.rvHome?.layoutManager =
            object : LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }
            }


        //viewModel.refresh()
        viewModel.listCoins.observe(viewLifecycleOwner, {
            checkResponseForView(it) {
                val coinListModel: List<CoinListModel> = it.data!!
                //Log.i("OnRecieved", "onViewCreated: +${coinListModel[1].name}")
                bining?.constParent?.visibility = View.VISIBLE
                adapter.submitList(coinListModel)
            }
        })


        bining?.rvHome?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                (bining!!.rvHome.layoutManager as LinearLayoutManager).orientation
            )
        )
        bining?.rvHome?.adapter = adapter

        bining?.priceSort?.setOnClickListener {
            setShimmerIndicator(true)
            bining?.constParent?.visibility = View.GONE
            viewModel.onChangeSort(HomeViewModel.SortType.Price("price"))
        }
        _binding?.MarketCapSort?.setOnClickListener {
            setShimmerIndicator(true)
            bining?.constParent?.visibility = View.GONE
            viewModel.onChangeSort(HomeViewModel.SortType.MarketCap("marketCap"))
        }

        viewModel.listLiveData.observe(viewLifecycleOwner){list->
            setShimmerIndicator(false)
            bining?.constParent?.visibility = View.VISIBLE
                adapter.submitList(list)

        }

    }

    fun setUpSpinners() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            _binding?.apply {
                timeSpinner.alpha = 0.5F
            }
        }

        _binding?.timeSpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                var isUp = false
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (!isUp) {
                        //setShimmerIndicator(true)
                       //bining?.constParent?.visibility = View.GONE
                        viewModel.onChangeSort(HomeViewModel.SortType.Time("${timeList[p2]}"))
                        _binding?.ivTimeArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                    } else {
                        _binding?.ivTimeArrow?.setImageResource(R.drawable.ic_arrow_up_spinner)
                        isUp = true
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }

        var timeListAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeList)

        _binding?.apply {
            timeSpinner.adapter = timeListAdapter
        }


        _binding?.timeSpinner?.setOnTouchListener { view, motionEvent ->

            if (MotionEvent.ACTION_DOWN == motionEvent.action) {
                _binding?.ivTimeArrow?.setImageResource(R.drawable.ic_arrow_down_spinner)
            }
            view.performClick()
        }
    }

    override fun onStop() {
        super.onStop()
        setShimmerIndicator(false, HomeShimmer = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onStart() {
        super.onStart()
        bining?.constParent?.visibility = View.VISIBLE
    }
}
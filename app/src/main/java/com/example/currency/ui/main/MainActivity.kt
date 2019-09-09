package com.example.currency.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.currency.view_model.CurrencyViewModel
import com.example.currency.R
import com.example.currency.data.entity.Rate
import com.example.currency.data.entity.RatesResponse
import com.example.currency.data.network.ApiResponse
import com.example.currency.databinding.MainActivityBinding
import com.example.currency.ui.widgets.CurrencyAdapter
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    private lateinit var viewModel: CurrencyViewModel

    lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)

        initViews()

        progressBar.visibility=View.VISIBLE

        viewModel.getRates()

        viewModel.rates.observe(this, Observer {
            if (it == null)
                return@Observer
            if (it.isSuccessful) {
                progressBar.visibility=View.GONE
                Timber.e("Response = ${it.body?.rates?.size ?: 0}")
                val temps = compareCurrencyRates(it)
                adapter.items = temps
                adapter.notifyDataSetChanged()
            } else {
                Timber.e(it.message?.message)
                progressBar.visibility=View.GONE
            }
        })
    }

    private fun compareCurrencyRates(it: ApiResponse<RatesResponse>): ArrayList<Rate> {
        val temps = ArrayList<Rate>()
        for (i in it.body?.rates ?: arrayListOf()) {
            var isFound = false
            for (j in adapter.items) {
                if (i.symbol.equals(j.symbol)) {
                    isFound = true
                    temps.add(Rate(i.symbol, i.price, i.price >= j.price))
                }
            }

            if (!isFound) {
                temps.add(Rate(i.symbol, i.price, true))
            }
        }
        return temps
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@MainActivity.viewModel
            executePendingBindings()

            adapter = CurrencyAdapter(arrayListOf())
            rvItems.adapter = adapter

        }
    }
}

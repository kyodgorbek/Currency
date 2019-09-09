package com.example.currency.ui.widgets

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.data.entity.Rate
import com.example.currency.databinding.CurrencyItemBinding

class CurrencyViewHolder(var binding: CurrencyItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context

    fun setItem(input: Rate) {
        binding.apply {
            item = input
            tvCurrencyFrom.text = input.symbol.subSequence(0, 3)
            tvCurrencyTo.text = input.symbol.subSequence(3, 6)
            tvCurrencyRate.text = input.price
            tvCurrencyRate.setTextColor(
                ContextCompat.getColor(
                    context, if (input.isHigher) R.color.colorCurrencyUp else R.color.colorCurrencyDown
                )
            )
            if (item!!.isHigher)
                imgCurrencyStatus.setImageResource(R.drawable.ic_trending_up)
            else
                imgCurrencyStatus.setImageResource(R.drawable.ic_trending_down)
        }
    }
}
package com.example.currency.ui.widgets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.data.entity.Rate
import com.example.currency.databinding.CurrencyItemBinding

class CurrencyAdapter(var items: List<Rate>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemBinding: CurrencyItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.currency_item, parent, false)


        return CurrencyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as CurrencyViewHolder
        holder.setItem(items[position])
    }
}
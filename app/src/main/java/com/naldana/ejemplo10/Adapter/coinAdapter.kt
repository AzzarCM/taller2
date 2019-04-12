package com.naldana.ejemplo10.Adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.model.infoCoins
import kotlinx.android.synthetic.main.info_coins.view.*

class coinAdapter(val items: List<infoCoins>) : RecyclerView.Adapter<coinAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_coins, null, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: infoCoins) = with(itemView) {
            tv_country.text = item.country
            tv_name.text = item.name
        }
    }

}
package com.naldana.ejemplo10.Adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.model.infoCoins
import kotlinx.android.synthetic.main.activity_detail_coin.*
import kotlinx.android.synthetic.main.info_coins.view.*

class coinAdapter(val items: List<infoCoins>, val clickListener: (infoCoins) -> Unit) : RecyclerView.Adapter<coinAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_coins, null, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: infoCoins, clickListener: (infoCoins) -> Unit) = with(itemView) {
            Glide.with(this)
                .load(item.img)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgview_icono_moneda)
            tv_country.text = item.country
            tv_name.text = item.name
            this.setOnClickListener{
                clickListener(item)
            }
        }
    }

}
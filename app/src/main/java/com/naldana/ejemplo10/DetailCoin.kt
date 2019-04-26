package com.naldana.ejemplo10

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.naldana.ejemplo10.model.infoCoins
import kotlinx.android.synthetic.main.activity_detail_coin.*

class DetailCoin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_coin)
        val reciever: infoCoins = intent?.extras?.getParcelable("COIN") ?: infoCoins()
        var instance = FragmentSecond.newIntance(reciever)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_content, instance).commit()
    }
}

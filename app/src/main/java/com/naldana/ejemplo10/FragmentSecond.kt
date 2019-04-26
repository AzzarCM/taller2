package com.naldana.ejemplo10

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.naldana.ejemplo10.model.infoCoins
import kotlinx.android.synthetic.main.fragment_fragment_second.*
import kotlinx.android.synthetic.main.fragment_fragment_second.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentSecond.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentSecond.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentSecond : Fragment() {
    lateinit var globalView : View
    var listCoin : infoCoins = infoCoins()

    companion object{
        fun newIntance(listCoin : infoCoins) : FragmentSecond{
            var instance = FragmentSecond()
            instance.listCoin = listCoin
            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        globalView = inflater.inflate(R.layout.fragment_fragment_second, container, false)
        init(listCoin)
        return globalView
    }

    fun init(coin: infoCoins){
        Glide.with(this)
            .load(coin.imgBanderaPais)
            .placeholder(R.drawable.ic_launcher_background)
            .into(globalView.app_bar_image_viewer)
        globalView.collapsingtoolbarviewer.title = coin.country
        globalView.tv_name.text=coin.name
        globalView.tv_value.text=coin.value
        globalView.tv_us_value.text=coin.value_us
        globalView.tv_year.text=coin.year
        globalView.tv_is_available.text=coin.isAvaliable.toString()
        globalView.tv_review.text=coin.review

    }
}

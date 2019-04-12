package com.naldana.ejemplo10.Network

import android.net.Uri
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkUtils {

    companion object {
        val COINS_API_BASE = "https://coinsapirestfulll.herokuapp.com/api/coin"
        @JvmStatic fun buiURL() : URL{
            var uri : Uri = Uri.parse(COINS_API_BASE).buildUpon().build()

            lateinit var url : URL

            try {
                url = URL(uri.toString())
            } catch (e: MalformedURLException){
                e.printStackTrace()
            }

            return url
        }

        @Throws(IOException::class)
        @JvmStatic fun getResponseFromHttpUrl(url: URL):String{
            val urlConnection = url.openConnection() as HttpURLConnection
            try {
                val `in` = urlConnection.inputStream

                val scanner = Scanner(`in`)
                scanner.useDelimiter("\\A")

                val hasInput = scanner.hasNext()
                return if(hasInput){
                    scanner.next()
                }else{
                    ""
                }
            }finally {
                urlConnection.disconnect()
            }
        }
    }

}
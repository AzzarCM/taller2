package com.naldana.ejemplo10

import android.content.ClipData
import android.content.ContentValues
import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.provider.BaseColumns
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.*
import android.support.v7.widget.RecyclerView.LayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.google.gson.Gson
import com.naldana.ejemplo10.Adapter.coinAdapter
import com.naldana.ejemplo10.Network.NetworkUtils
import com.naldana.ejemplo10.data.Database
import com.naldana.ejemplo10.data.DatabaseContract
import com.naldana.ejemplo10.model.infoAllCoin
import com.naldana.ejemplo10.model.infoCoins
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var lista: ArrayList<infoCoins> = ArrayList<infoCoins>()
    var dbHelper = Database(this)

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    var twoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO (9) Se asigna a la actividad la barra personalizada
        setSupportActionBar(toolbar)


        // TODO (11) Permite administrar el DrawerLayout y el ActionBar

        // TODO (11.1) Implementa las caracteristicas recomendas
        // TODO (11.2) Un DrawerLayout (drawer_layout)
        // TODO (11.3) Un lugar donde dibujar el indicador de apertura (la toolbar)
        // TODO (11.4) Una String que describe el estado de apertura
        // TODO (11.5) Una String que describe el estado cierre
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        // TODO (12) Con el Listener Creado se asigna al  DrawerLayout
        drawer_layout.addDrawerListener(toggle)


        // TODO(13) Se sincroniza el estado del menu con el LISTENER
        toggle.syncState()

        // TODO (14) Se configura el listener del menu que aparece en la barra lateral
        // TODO (14.1) Es necesario implementar la inteface {{@NavigationView.OnNavigationItemSelectedListener}}
        nav_view.setNavigationItemSelectedListener(this)

        // TODO (20) Para saber si estamos en modo dos paneles
        if (fragment_content != null) {
            twoPane = true
        }

        /*
         * TODO (Instrucciones)Luego de leer todos los comentarios añada la implementación de RecyclerViewAdapter
         * Y la obtencion de datos para el API de Monedas
         */

        //initRecycler()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Syncing con API y actualizando la base de datos sql", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            FetchCoins().execute()
        }
        try {
            initRecycler(readCoins())
        }finally {
            Log.e("error", "Error al recuperar la tabla")
        }

        if (readData("").size == 0){
            FetchCoins().execute("")
        }

    }

    private fun coinItemClicked(item: infoCoins) {
        val coinBundle = Bundle()
        coinBundle.putParcelable("COIN", item)
        startActivity(Intent(this, DetailCoin::class.java).putExtras(coinBundle))
    }

    private fun iniFragment(item: infoCoins){
        var instance = FragmentSecond.newIntance(item)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_content, instance).commit()
    }

    private fun coinItemClickedLand(item: infoCoins){
        iniFragment(item)
    }

    private lateinit var viewAdapter: coinAdapter
    private lateinit var viewManager: LayoutManager

    fun initRecycler(coins: ArrayList<infoCoins>) {
        if (this.resources.configuration.orientation == 2
            || this.resources.configuration.orientation == 4
        ) {
            viewManager = LinearLayoutManager(this)
            viewAdapter = coinAdapter(coins, { coinItem: infoCoins -> coinItemClickedLand(coinItem) })
            iniFragment(infoCoins())
        } else {
            viewManager = GridLayoutManager(this, 2)
            viewAdapter = coinAdapter(coins, { coinItem: infoCoins -> coinItemClicked(coinItem) })
        }

        recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }


    private inner class FetchCoins : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            var url: URL = NetworkUtils.buiURL()
            lista.clear()
            try {
                var result: String = NetworkUtils.getResponseFromHttpUrl(url)
                var gson: Gson = Gson()
                var element: infoAllCoin = gson.fromJson(result, infoAllCoin::class.java)
                for (i in 0..(element.datos.size - 1)) {
                    var dato: infoCoins = infoCoins(
                        element.datos.get(i).value.toString(),
                        element.datos.get(i).value_us.toString(),
                        element.datos.get(i).year,
                        element.datos.get(i).review,
                        element.datos.get(i).isAvaliable,
                        element.datos.get(i).img,
                        element.datos.get(i).name.toString(),
                        element.datos.get(i).country,
                        element.datos.get(i).__v,
                        element.datos.get(i).imgBanderaPais
                    )
                    lista.add(dato)
                }

                return result
            } catch (e: IOException) {
                e.printStackTrace()
                return ""
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            val db = dbHelper.writableDatabase
            dbHelper.onUpgrade(db, 0, 0)
            for (coin in lista) {
                val values = ContentValues().apply {
                    put(DatabaseContract.CoinEntry.COLUMN_VALUE, coin.value)
                    put(DatabaseContract.CoinEntry.COLUMN_VALUE_US, coin.value_us)
                    put(DatabaseContract.CoinEntry.COLUMN_YEAR, coin.year)
                    put(DatabaseContract.CoinEntry.COLUMN_REVIEW, coin.review)
                    put(DatabaseContract.CoinEntry.COLUMN_ISAVALIABLE, coin.isAvaliable)
                    put(DatabaseContract.CoinEntry.COLUMN_IMG, coin.img)
                    //put(DatabaseContract.CoinEntry.COLUMN_ID, coin._id)
                    put(DatabaseContract.CoinEntry.COLUMN_NAME, coin.name)
                    put(DatabaseContract.CoinEntry.COLUMN_COUNTRY, coin.country)
                    put(DatabaseContract.CoinEntry.COLUMN_V, coin.__v)
                    put(DatabaseContract.CoinEntry.COLUMN_IMGBANDERAPAIS, coin.imgBanderaPais)

                }
                val newRowId = db?.insert(DatabaseContract.CoinEntry.TABLE_NAME, null, values)
                if (newRowId != -1L) {
                    //mAdapter.setPersonas(readPersonas())
                }
            }

            initRecycler(lista)
            // TODO (10) Click Listener para el boton flotante
        }
    }

    private fun readData(search : String) : ArrayList<infoCoins> {
        var dbBase = Database(this)
        //Hace que la base de datos este en modo lectura.
        val db = dbBase.readableDatabase

        //Hace un objeto referente a la tabla, con un arrayList.
        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseContract.CoinEntry.COLUMN_VALUE,
            DatabaseContract.CoinEntry.COLUMN_VALUE_US,
            DatabaseContract.CoinEntry.COLUMN_YEAR,
            DatabaseContract.CoinEntry.COLUMN_REVIEW,
            DatabaseContract.CoinEntry.COLUMN_ISAVALIABLE,
            DatabaseContract.CoinEntry.COLUMN_IMG,
            DatabaseContract.CoinEntry.COLUMN_NAME,
            DatabaseContract.CoinEntry.COLUMN_COUNTRY,
            DatabaseContract.CoinEntry.COLUMN_V,
            DatabaseContract.CoinEntry.COLUMN_IMGBANDERAPAIS
        )

        val whereS = "${DatabaseContract.CoinEntry.COLUMN_COUNTRY} like '${search}%'"

        //Ejecuta la sentencia select para hacer la busqueda en la tabla.
        val cursor = db.query(
            DatabaseContract.CoinEntry.TABLE_NAME,
            projection,
            whereS,
            null,
            null,
            null,
            null
        )

        var lista = ArrayList<infoCoins>()

        with(cursor) {
            while (moveToNext()) {
                var personList = infoCoins(
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_VALUE)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_VALUE_US)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_YEAR)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_REVIEW)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_ISAVALIABLE)) > 0,
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_IMG)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_COUNTRY)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_V)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_IMGBANDERAPAIS))
                )
                lista.add(personList)
            }
        }

        return lista
    }

    // TODO (16) Para poder tener un comportamiento Predecible
    // TODO (16.1) Cuando se presione el boton back y el menu este abierto cerralo
    // TODO (16.2) De lo contrario hacer la accion predeterminada
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // TODO (17) LLena el menu que esta en la barra. El de tres puntos a la derecha
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // TODO (18) Atiende el click del menu de la barra
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun searchForCountry(country: String) {
        var listaS: ArrayList<infoCoins> = ArrayList<infoCoins>()
        listaS = readData(country)
        initRecycler(listaS)
    }

    // TODO (14.2) Funcion que recibe el ID del elemento tocado
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            // TODO (14.3) Los Id solo los que estan escritos en el archivo de MENU
            R.id.nav_all -> {
                initRecycler(readCoins())
            }
            R.id.nav_sv -> {
                searchForCountry("El Salvador")
            }
            R.id.nav_mx -> {
                searchForCountry("Mexico")
            }
            R.id.nav_usa -> {
                searchForCountry("USA")
            }
            R.id.nav_vn -> {
                searchForCountry("Venezuela")
            }
            R.id.nav_gt -> {
                searchForCountry("Guatemala")
            }
        }

        // TODO (15) Cuando se da click a un opcion del menu se cierra de manera automatica
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun readCoins(): ArrayList<infoCoins>{

// TODO(13) Para obtener los datos almacenados, es necesario solicitar una instancia de lectura de la base de datos.
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseContract.CoinEntry.COLUMN_VALUE,
            DatabaseContract.CoinEntry.COLUMN_VALUE_US,
            DatabaseContract.CoinEntry.COLUMN_YEAR,
            DatabaseContract.CoinEntry.COLUMN_REVIEW,
            DatabaseContract.CoinEntry.COLUMN_ISAVALIABLE,
            DatabaseContract.CoinEntry.COLUMN_NAME,
            DatabaseContract.CoinEntry.COLUMN_COUNTRY,
            DatabaseContract.CoinEntry.COLUMN_V,
            DatabaseContract.CoinEntry.COLUMN_IMGBANDERAPAIS,
            DatabaseContract.CoinEntry.COLUMN_IMG
        )

        val sortOrder = "${DatabaseContract.CoinEntry.COLUMN_COUNTRY} ASC"

        val cursor = db.query(
            DatabaseContract.CoinEntry.TABLE_NAME, // nombre de la tabla
            projection, // columnas que se devolverán
            null, // Columns where clausule
            null, // values Where clausule
            null, // Do not group rows
            null, // do not filter by row
            sortOrder // sort order
        )

        var listaSQL: ArrayList<infoCoins> = ArrayList<infoCoins>()

        with(cursor) {
            while (moveToNext()) {
                var coin = infoCoins(
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_VALUE)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_VALUE_US)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_YEAR)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_REVIEW)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_ISAVALIABLE)).toBoolean(),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_IMG)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_COUNTRY)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_V)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_IMGBANDERAPAIS))
                )

                listaSQL.add(coin)
            }
        }
        return listaSQL
    }
}

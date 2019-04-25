package com.naldana.ejemplo10.data

import android.provider.BaseColumns

// TODO (3) La definición del objeto DatabaseContract sirve para establecer una capa de abstracción que define la estructurade la base de datos.
// TODO (3.1) Cada tabla en la base de datos es definida como un objeto dentro del contrato de base de datos.
// TODO (3.2) Al utilizar la palabra reservada object define  un objeto de tipo Singleton

object DatabaseContract {

    // TODO (4) Para cada tabla en la base de datos se define una entrada (Entry)
    // TODO (4.1) Cada entrada debe definir el nombre de la tabla y el de cada columna
    // TODO (4.2) Debe de heredar de BaseColumns. Para heredar el nombre de las columnas tradicionales como _ID, _COUNT y otros comportamientos básicos.
    object CoinEntry : BaseColumns { // Se guardan los datos relevantes de la tabla, como su nombre y sus campos.

        const val TABLE_NAME = "coin"

        // Se crea una constante por cada columna de la tabla.
        const val COLUMN_VALUE = "value"
        const val COLUMN_VALUE_US = "value_us"
        const val COLUMN_YEAR = "year"
        const val COLUMN_REVIEW = "review"
        const val COLUMN_ISAVALIABLE = "isAvaliable"
        const val COLUMN_IMG = "img"
       // const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_V = "__v"
        const val COLUMN_IMGBANDERAPAIS = "imgBanderaPais"

    }
}
package com.naldana.ejemplo10.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

// TODO(6) Se crea una cadena de texto con la instrucción de creación de tabla usando SQL.
// TODO (6.1) El nombre de la tabla y el de las columnas se asignan según los datos definidos en el contrato.
private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${DatabaseContract.CoinEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DatabaseContract.CoinEntry.COLUMN_VALUE} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_VALUE_US} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_YEAR} TEXT,"+
            "${DatabaseContract.CoinEntry.COLUMN_REVIEW} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_ISAVALIABLE} TEXT,"+
            "${DatabaseContract.CoinEntry.COLUMN_NAME} TEXT,"+
            "${DatabaseContract.CoinEntry.COLUMN_COUNTRY} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_V} TEXT,"+
            "${DatabaseContract.CoinEntry.COLUMN_IMGBANDERAPAIS} TEXT,"+
            "${DatabaseContract.CoinEntry.COLUMN_IMG} TEXT)"


// TODO(7) Se crea una cadena de texto con la instrucción de eliminación de tabla usando SQL.
// TODO(7.1) Esto se hace para cuando sea necesario actualizar la estructura de la tabla.

private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${DatabaseContract.CoinEntry.TABLE_NAME}"

private const val SQL_BORRAR_DATOS_TABLA=
    "DELETE FROM ${DatabaseContract.CoinEntry.TABLE_NAME}"

// TODO(5) Para poder crear una base de datos en el dispositivo móvil es necesario utilizar una instancia de SQLiteOperHelper.
// TODO(5.1) SQLiteOperHelper tiene su propio ciclo de vida: Creación de base de datos, cambios de versión (upgrading y downgrading).
// TODO(5.2) Para crear un SQLiteOperHelper se necesita un contexto, el nombre de la base de datos, y la versión de base de datos.
// TODO(5.3) Si se desea tener más de una base de datos, se puede crear varios SQLiteOperHelper o crear uno capaz de saber qué base de datos se está creando.
class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // TODO(8) Este método se ejecuta cuando es necesario crear el esquema de base de datos.
    // TODO(8.1) Este recibe como parámetro una instancia de SQLiteDatabase y que permite ejecutar SQL de DDL.
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES) // TODO (9) Ejecuta el DDL.
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO (10) Cuando la base de datos ya existe y se define una nueva versión, se deben ejecutar scripts de migración.
        db.execSQL(SQL_DELETE_ENTRIES) // Como esta base de datos se utilizará como caché, no nos interesa conservar los datos almacenados en la versión anterior.
        onCreate(db) // Se vuelve a crear la base.
    }

    // TODO(11) Se definen en constantes, el nombre de la base de datos y la versión
    companion object {
        const val DATABASE_NAME = "miprimerabaseV2.db"
        const val DATABASE_VERSION = 1
    }
}
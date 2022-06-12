package com.example.ml_kit_text_recognition

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DataBase(context: Context) : SQLiteOpenHelper(context, "Products", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE PRODUCTS(PID INTEGER PRIMARY KEY AUTOINCREMENT,PNAME TEXT, PING TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    companion object {
        fun getDatabase(context: Context): DataBase {
            return DataBase(context)
        }
    }
}

package com.example.ml_kit_text_recognition

import android.content.Context
import android.content.pm.ApplicationInfo
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.content.res.AppCompatResources

open class DataBase(context: Context) : SQLiteOpenHelper(context, "Products", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE PRODUCTS(PID INTEGER PRIMARY KEY AUTOINCREMENT,PNAME TEXT, PING TEXT)")
        db?.execSQL("INSERT INTO PRODUCTS(PNAME, PING) VALUES('Coca-Cola', 'cukier')")
        db?.execSQL("INSERT INTO PRODUCTS(PNAME, PING) VALUES('Fanta', 'pomarancz')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    companion object {
        fun getDatabase(context: Context): DataBase {
            return DataBase(context)
        }
    }
}

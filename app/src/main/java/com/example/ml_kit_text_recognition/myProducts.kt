package com.example.ml_kit_text_recognition

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class myProducts : AppCompatActivity() {

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel = ViewModelProvider(this).get(myProductsViewModel::class.java)
        viewModel.binding = DataBindingUtil.setContentView(this, R.layout.activity_my_products)




        viewModel.binding.glutenFreeTitle.setOnClickListener {
            if (viewModel.binding.glutenFreeList.visibility == View.GONE) {
                viewModel.binding.glutenFreeList.visibility = View.VISIBLE
                var helper = DataBase.getDatabase(applicationContext)
                var db = helper.writableDatabase
                //db?.execSQL("INSERT INTO PRODUCTS(PNAME, PING) VALUES('whitw', 'pomarancz')")

                val cur: Cursor = db.rawQuery(
                    "SELECT * FROM PRODUCTS",
                    null
                )
                val arrayAdapter: ArrayAdapter<*>
                val users = mutableListOf<String>()
                if (cur.moveToFirst()) {
                    do {
                        val str = cur.getString(1)
                        users.add(str)
                    } while (cur.moveToNext())
                }

                for(item in users)
                {
                    println(item)
                }
                arrayAdapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, users)
                viewModel.binding.glutenFreeList.adapter = arrayAdapter
            }
            else
            {
                viewModel.binding.glutenFreeList.visibility = View.GONE
            }
        }

        viewModel.binding.notGlutenFreeTitle.setOnClickListener {
            if (viewModel.binding.notGlutenFreeList.visibility == View.GONE) {
                viewModel.binding.notGlutenFreeList.visibility = View.VISIBLE
                var helper = DBNotGluten.getDatabase(applicationContext)
                var db = helper.writableDatabase
                //db?.execSQL("INSERT INTO PRODUCTS(PNAME, PING) VALUES('whitw', 'pomarancz')")

                val cur: Cursor = db.rawQuery(
                    "SELECT * FROM NOTGLUTENFREE",
                    null
                )
                val arrayAdapter: ArrayAdapter<*>
                val users = mutableListOf<String>()
                if (cur.moveToFirst()) {
                    do {
                        val str = cur.getString(1)
                        users.add(str)
                    } while (cur.moveToNext())
                }

                for(item in users)
                {
                    println(item)
                }
                arrayAdapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, users)
                viewModel.binding.notGlutenFreeList.adapter = arrayAdapter
            }
            else
            {
                viewModel.binding.notGlutenFreeList.visibility = View.GONE
            }
        }

        viewModel.binding.notVegetarianTitle.setOnClickListener {
            if (viewModel.binding.notVegetarianList.visibility == View.GONE) {
                viewModel.binding.notVegetarianList.visibility = View.VISIBLE
                var helper = DBNotVegetarian.getDatabase(applicationContext)
                var db = helper.writableDatabase
                //db?.execSQL("INSERT INTO PRODUCTS(PNAME, PING) VALUES('whitw', 'pomarancz')")

                val cur: Cursor = db.rawQuery(
                    "SELECT * FROM NOTVEGETARIAN",
                    null
                )
                val arrayAdapter: ArrayAdapter<*>
                val users = mutableListOf<String>()
                if (cur.moveToFirst()) {
                    do {
                        val str = cur.getString(1)
                        users.add(str)
                    } while (cur.moveToNext())
                }

                for(item in users)
                {
                    println(item)
                }
                arrayAdapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, users)
                viewModel.binding.notVegetarianList.adapter = arrayAdapter
            }
            else
            {
                viewModel.binding.notVegetarianList.visibility = View.GONE
            }
        }

        viewModel.binding.vegetarianTitle.setOnClickListener {
            if (viewModel.binding.vegetarianList.visibility == View.GONE) {
                viewModel.binding.vegetarianList.visibility = View.VISIBLE
                var helper = DBVegetarian.getDatabase(applicationContext)
                var db = helper.writableDatabase
                //db?.execSQL("INSERT INTO PRODUCTS(PNAME, PING) VALUES('whitw', 'pomarancz')")

                val cur: Cursor = db.rawQuery(
                    "SELECT * FROM VEGETARIAN",
                    null
                )
                val arrayAdapter: ArrayAdapter<*>
                val users = mutableListOf<String>()
                if (cur.moveToFirst()) {
                    do {
                        val str = cur.getString(1)
                        users.add(str)
                    } while (cur.moveToNext())
                }

                for(item in users)
                {
                    println(item)
                }
                arrayAdapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, users)
                viewModel.binding.vegetarianList.adapter = arrayAdapter
            }
            else
            {
                viewModel.binding.vegetarianList.visibility = View.GONE
            }
        }
    }

}

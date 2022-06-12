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


        var helper = DataBase.getDatabase(applicationContext)
        var db = helper.readableDatabase

        viewModel.binding.glutenFreeTitle.setOnClickListener {
            if (viewModel.binding.glutenFreeList.visibility == View.GONE) {
                viewModel.binding.glutenFreeList.visibility = View.VISIBLE

                val cur: Cursor = db.rawQuery(
                    "SELECT * FROM PRODUCTS",
                    null
                )
                val temp = ArrayList<Any>()
                val arrayAdapter: ArrayAdapter<*>
                val users = mutableListOf<String>()
                if (cur.moveToFirst()) {
                    do {
                        val str = cur.getString(0)
                        users.add(str)
                    } while (cur.moveToNext())
                }

                for(item in users)
                {
                    println(item)
                }
                // access the listView from xml file
                arrayAdapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, users)
                viewModel.binding.glutenFreeList.adapter = arrayAdapter
            }
            else
                // access the listView from xml file
            {
                viewModel.binding.glutenFreeList.visibility = View.GONE
            }
        }
    }

}

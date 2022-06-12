package com.example.ml_kit_text_recognition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class myProducts : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel = ViewModelProvider(this).get(myProductsViewModel::class.java)
        viewModel.binding = DataBindingUtil.setContentView(this, R.layout.activity_my_products)

        viewModel.binding.glutenFreeTitle.setOnClickListener {
            if (viewModel.binding.glutenFreeList.visibility == View.GONE) {
                viewModel.binding.glutenFreeList.visibility = View.VISIBLE
                val arrayAdapter: ArrayAdapter<*>
                val users = mutableListOf<String>()
                val str = intent.getStringExtra("message_key")
                if (str != null) {
                    users.add(str)
                }

                // access the listView from xml file
                arrayAdapter = ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, users)
                viewModel.binding.glutenFreeList.adapter = arrayAdapter
            }
            else
                viewModel.binding.glutenFreeList.visibility = View.GONE
        }
    }

}

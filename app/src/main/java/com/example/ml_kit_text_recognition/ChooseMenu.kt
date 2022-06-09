package com.example.ml_kit_text_recognition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

class ChooseMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel = ViewModelProvider(this).get(ChooseMenuViewModel::class.java)
        viewModel.binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_menu)

        viewModel.binding.productImage.setImageResource(viewModel.camera)
        viewModel.binding.menuImage.setImageResource(viewModel.directory)

        viewModel.binding.menuButton.setOnClickListener{
            val m = Intent(this@ChooseMenu, menuScreen::class.java)
            startActivity(m)
        }

        viewModel.binding.productButton.setOnClickListener{
            val p = Intent(this@ChooseMenu, productScreen::class.java)
            startActivity(p)
        }
    }
}
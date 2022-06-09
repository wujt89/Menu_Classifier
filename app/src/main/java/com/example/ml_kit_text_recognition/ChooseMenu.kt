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

        viewModel.binding.takeImagesImage.setImageResource(viewModel.camera)
        viewModel.binding.importImagesImage.setImageResource(viewModel.directory)

        viewModel.binding.importImagesButton.setOnClickListener{
            val i = Intent(this@ChooseMenu, productScreen::class.java)
            startActivity(i)
        }
    }
}
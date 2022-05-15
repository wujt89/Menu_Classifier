package com.example.ml_kit_text_recognition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ml_kit_text_recognition.databinding.ActivityFirstScreenBinding


class firstScreen : AppCompatActivity() {
    private lateinit var binding: ActivityFirstScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_screen)


        binding.checkBtn.setOnClickListener {
            val i = Intent(this@firstScreen, ChooseMenu::class.java)
            startActivity(i)
        }
    }
}
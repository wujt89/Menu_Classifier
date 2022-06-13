package com.example.ml_kit_text_recognition

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ml_kit_text_recognition.databinding.ActivityFirstScreenBinding


class firstScreen : AppCompatActivity() {
    private lateinit var binding: ActivityFirstScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_screen)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            {
                val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, 112)
            }
        }

        binding.checkBtn.setOnClickListener {
            val i = Intent(this@firstScreen, ChooseMenu::class.java)
            startActivity(i)
        }

        binding.addProduct.setOnClickListener {
            val i = Intent(this@firstScreen, myProducts::class.java)
            startActivity(i)
        }
    }
}
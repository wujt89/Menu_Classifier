package com.example.ml_kit_text_recognition

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.ml_kit_text_recognition.databinding.ActivityFirstScreenBinding
import com.example.ml_kit_text_recognition.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)



        viewModel.binding.btnStart.setOnClickListener {
            viewModel.startTextRecognition()
            viewModel.binding.progressBar.visibility = View.VISIBLE
        }

//        viewModel.binding.image.setOnClickListener {
//            viewModel.dispatchTakePictureIntent()
//        }
        viewModel.binding.imageView.setImageResource(viewModel.img_id)
        viewModel.selectedImage = BitmapFactory.decodeResource(resources, viewModel.img_id)

        val content:Array<String> = arrayOf("img", "twitt", "menu2")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, content)
        viewModel.binding.spinner.adapter = adapter
        viewModel.binding.spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?){

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.binding.tvContent.text = ""
                if(position == 0)
                {
                    viewModel.binding.imageView.setImageResource(viewModel.img_id)
                    viewModel.selectedImage = BitmapFactory.decodeResource(resources,viewModel.img_id)
                }

                else if(position == 1)
                {
                    viewModel.binding.imageView.setImageResource(viewModel.twitt)
                    viewModel.selectedImage = BitmapFactory.decodeResource(resources,viewModel.twitt)
                }

                else
                {
                    viewModel.binding.imageView.setImageResource(viewModel.menu2)
                    viewModel.selectedImage = BitmapFactory.decodeResource(resources,viewModel.menu2)
                }
            }
        }
    }
}
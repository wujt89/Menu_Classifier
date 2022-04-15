package com.example.ml_kit_text_recognition

import android.app.WallpaperColors.fromBitmap
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewParent
import android.widget.*
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {

    private val TAG  = "ML_Kit_text_recognition"
    private val img_id = R.drawable.img
    private val twitt = R.drawable.twitt



    private lateinit var  btn:Button
    private lateinit var  tvContent:TextView
    private lateinit var  pb:ProgressBar
    private lateinit var  imageView:ImageView
    private lateinit var  spinner:Spinner

    private var selectedImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn_start)
        tvContent = findViewById(R.id.tv_content)
        pb = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
        spinner = findViewById(R.id.spinner)

        btn.setOnClickListener {
            startTextRecognition()
            pb.visibility = View.VISIBLE
        }

        imageView.setImageResource(img_id)
        selectedImage = BitmapFactory.decodeResource(resources, img_id)

        val content:Array<String> = arrayOf("img", "twitt")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, content)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?){

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tvContent.text = ""
                if(position == 0)
                {
                    imageView.setImageResource(img_id)
                    selectedImage = BitmapFactory.decodeResource(resources,img_id)
                }

                else
                {
                    imageView.setImageResource(twitt)
                    selectedImage = BitmapFactory.decodeResource(resources,twitt)
                }
            }
        }
    }

    private fun startTextRecognition()
    {
        val inputImage = InputImage.fromBitmap(selectedImage!!,0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(inputImage)
            .addOnSuccessListener {
                pb.visibility = View.GONE
                tvContent.text = it.text

            }
            .addOnFailureListener{
                Log.d(TAG, "Succesful Recognition", it)
            }
    }
}
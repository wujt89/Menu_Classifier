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
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    private val TAG  = "ML_Kit_text_recognition"
    private val img_id = R.drawable.img
    private val twitt = R.drawable.twitt
    private val menu2 = R.drawable.menu2

    var bitmapArray = ArrayList<Bitmap>()
    var glutenArray = listOf<String>("bread")
    val meatArray = listOf<String>("steak", "chicken")



    private lateinit var  btn:Button
    private lateinit var  tvContent:TextView
    private lateinit var  pb:ProgressBar
    private lateinit var  imageView:ImageView
    private lateinit var  spinner:Spinner
    private lateinit var  takeImage:Button

    private var selectedImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn_start)
        tvContent = findViewById(R.id.tv_content)
        pb = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
        spinner = findViewById(R.id.spinner)
        takeImage = findViewById(R.id.image)

        btn.setOnClickListener {
            startTextRecognition()
            pb.visibility = View.VISIBLE
        }

        takeImage.setOnClickListener {
            dispatchTakePictureIntent()
        }
        imageView.setImageResource(img_id)
        selectedImage = BitmapFactory.decodeResource(resources, img_id)

        val content:Array<String> = arrayOf("img", "twitt", "menu2")
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

                else if(position == 1)
                {
                    imageView.setImageResource(twitt)
                    selectedImage = BitmapFactory.decodeResource(resources,twitt)
                }

                else
                {
                    imageView.setImageResource(menu2)
                    selectedImage = BitmapFactory.decodeResource(resources,menu2)
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

                handlingString(it)

            }
            .addOnFailureListener{
                Log.d(TAG, "Succesful Recognition", it)
            }
    }

    private fun handlingString(text : Text) {
        for (i in 0 until text.textBlocks.size) {
            var gluten = false
            var number = 0
            var meat = false
            val str = text.textBlocks.get(i).text
            val spannable = SpannableStringBuilder(str + " ")
            if (i % 2 != 0) {
                val slicedString = str.slice(13 until str.length)
                val arr = slicedString.split(", ")

                for (value in arr) {
                    if (value.lowercase() in glutenArray) {
                        gluten = true
                        val foundWord = value.toRegex().find(str)
                        if (foundWord != null) {
                            spannable.setSpan(
                                BackgroundColorSpan(Color.YELLOW),
                                foundWord.range.first, // start
                                foundWord.range.last+1, // end
                                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                            )
                        }
                    }
                    for (meatType in meatArray)
                    {
                        if (meatType in value.lowercase()) {
                            meat = true
                            val foundWord = value.toRegex().find(str)
                            if (foundWord != null) {
                                println(foundWord.value)
                                spannable.setSpan(
                                    BackgroundColorSpan(Color.RED),
                                    foundWord.range.first, // start
                                    foundWord.range.last + 1, // end
                                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                                )
                            }
                        }
                    }
                    number += 1
                }
                tvContent.append(spannable)
                if (!gluten) {
                    val spann = SpannableStringBuilder(" GF ")
                    spann.setSpan(
                        ForegroundColorSpan(Color.YELLOW),
                        1, // start
                        3, // end
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    tvContent.append(spann)
                }
                if (!meat) {
                    val spann = SpannableStringBuilder(" V ")
                    spann.setSpan(
                        ForegroundColorSpan(Color.GREEN),
                        1, // start
                        2, // end
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    tvContent.append(spann)
                }
            }
            else{
                tvContent.append(spannable)
            }

            tvContent.append("\n")
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            selectedImage = imageBitmap
        }
    }
}
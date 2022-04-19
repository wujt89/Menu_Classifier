package com.example.ml_kit_text_recognition

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    private val TAG  = "ML_Kit_text_recognition"
    private val img_id = R.drawable.img
    private val twitt = R.drawable.twitt

    var bitmapArray = ArrayList<Bitmap>()
    var glutenArray = listOf<String>("gryczana", "pęczak", "panierowany", "panierowane", "panierka")
    val meatArray = listOf<String>("wołowina", "wieprzowina", "kurczak")



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
                println(it.textBlocks.size)
                for (i in 0 until it.textBlocks.size)
                {
                    println(it.textBlocks.get(i).text)
                }

                println("lalalallalalalalalaa")

            }
            .addOnFailureListener{
                Log.d(TAG, "Succesful Recognition", it)
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
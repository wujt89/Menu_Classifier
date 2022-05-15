package com.example.ml_kit_text_recognition

import android.graphics.Bitmap
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.ml_kit_text_recognition.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivityViewModel: ViewModel(){
    lateinit var binding: ActivityMainBinding
    //private val REQUEST_IMAGE_CAPTURE = 1

    private val TAG  = "ML_Kit_text_recognition"
    val img_id = R.drawable.img
    val twitt = R.drawable.twitt
    val menu2 = R.drawable.menu2

    var bitmapArray = ArrayList<Bitmap>()
    private var glutenArray = listOf<String>("bread")
    private val meatArray = listOf<String>("steak", "chicken")



    var selectedImage: Bitmap? = null

    fun startTextRecognition()
    {
        val inputImage = InputImage.fromBitmap(selectedImage!!,0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(inputImage)
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE

                handlingString(it)

            }
            .addOnFailureListener{
                Log.d(TAG, "Succesful Recognition", it)
            }
    }

    fun handlingString(text : Text) {
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
                binding.tvContent.append(spannable)
                if (!gluten) {
                    val spann = SpannableStringBuilder(" GF ")
                    spann.setSpan(
                        ForegroundColorSpan(Color.YELLOW),
                        1, // start
                        3, // end
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    binding.tvContent.append(spann)
                }
                if (!meat) {
                    val spann = SpannableStringBuilder(" V ")
                    spann.setSpan(
                        ForegroundColorSpan(Color.GREEN),
                        1, // start
                        2, // end
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    binding.tvContent.append(spann)
                }
            }
            else{
                binding.tvContent.append(spannable)
            }

            binding.tvContent.append("\n")
        }
    }
//
//    fun dispatchTakePictureIntent() {
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        try {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//        } catch (e: ActivityNotFoundException) {
//            // display error state to the user
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
//        {
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            binding.imageView.setImageBitmap(imageBitmap)
//            selectedImage = imageBitmap
//        }
//    }
}
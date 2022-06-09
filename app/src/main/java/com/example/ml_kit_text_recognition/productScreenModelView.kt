package com.example.ml_kit_text_recognition

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ml_kit_text_recognition.databinding.ActivityMenuScreenBinding
import com.example.ml_kit_text_recognition.databinding.ActivityProductScreenBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class productScreenModelView : ViewModel() {

    lateinit var binding: ActivityProductScreenBinding
    val pickImage = 100
    val IMAGE_CAPTURE_CODE = 654
    var imageUri: Uri? = null
    var imageBitmap: Bitmap? = null
    private val TAG = "ML_Kit_text_recognition"
    val img_id = R.drawable.img
    val twitt = R.drawable.twitt
    val menu2 = R.drawable.menu2

    var flag = 0

    var bitmapArray = ArrayList<Bitmap>()
    private var glutenArray = listOf<String>("bread")
    private val meatArray = listOf<String>("steak", "chicken")


    var selectedImage: Bitmap? = null

    fun startTextRecognition(image: Bitmap) {
        val inputImage = InputImage.fromBitmap(image!!, 90)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(inputImage)
            .addOnSuccessListener {
                handlingString(it)
            }
            .addOnFailureListener {
                Log.d(TAG, "Succesful Recognition", it)
            }
    }


    fun rotateBitmap(source: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height, matrix, true
        )
    }

    fun handlingString(text: Text) {
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
                                foundWord.range.last + 1, // end
                                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                            )
                        }
                    }
                    for (meatType in meatArray) {
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
                binding.recognizedText.append(spannable)
                if (!gluten) {
                    val spann = SpannableStringBuilder(" GF ")
                    spann.setSpan(
                        ForegroundColorSpan(Color.YELLOW),
                        1, // start
                        3, // end
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    binding.recognizedText.append(spann)
                }
                if (!meat) {
                    val spann = SpannableStringBuilder(" V ")
                    spann.setSpan(
                        ForegroundColorSpan(Color.GREEN),
                        1, // start
                        2, // end
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    binding.recognizedText.append(spann)
                }
            } else {
                binding.recognizedText.append(spannable)
            }

            binding.recognizedText.append("\n")
        }
    }
}
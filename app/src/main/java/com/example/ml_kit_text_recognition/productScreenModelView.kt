package com.example.ml_kit_text_recognition

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.lifecycle.ViewModel
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
    var returnCode=100
    var ingGluten = ""
    var ingMeat = ""

    var flag = 0

    var bitmapArray = ArrayList<Bitmap>()
    private var glutenArray = listOf<String>("bread", "flour", "pasta", "wheat")
    private val meatArray = listOf<String>("steak", "chicken", "salmon", "prawns", "becon", "gelatine", "beef")


    var selectedImage: Bitmap? = null

    fun startTextRecognition(image: Bitmap) {
        val inputImage = InputImage.fromBitmap(image, 90)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(inputImage)
            .addOnSuccessListener {
                binding.recognizedText.text = ""
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

    private fun handlingString(text: Text) {
        var gluten = false
        println(text.text.toString())
        var meat = false
        returnCode=0
        var containGluten: MutableList<String> = ArrayList()
        var containMeat: MutableList<String> = ArrayList()
        for (i in 0 until text.textBlocks.size) {
            var number = 0
            val str = text.textBlocks[i].text
            val arr = str.split(", ")
            for (value in arr) {
                println(value)
                if (value.lowercase() in glutenArray) {
                    gluten = true

                    val foundWord = value.toRegex().find(str)
                    if (foundWord != null) {
                        containGluten.add(foundWord.value)
                    }
                }
                for (meatType in meatArray) {
                    if (meatType in value.lowercase()) {
                        meat = true

                        val foundWord = value.toRegex().find(str)
                        if (foundWord != null) {
                            containMeat.add(foundWord.value)

                        }
                    }
                }
                number += 1
            }
        }
        if (gluten)
        {
            returnCode+=10
            val spann = SpannableStringBuilder(" NOT Gluten Free ")
            spann.setSpan(
                ForegroundColorSpan(Color.rgb(245, 190,0)),
                1, // start
                16, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            binding.recognizedText.append("Product is ")
            binding.recognizedText.append(spann)
            binding.recognizedText.append("\n")
            ingGluten = ""
            for(item in containGluten)
            {
                binding.recognizedText.append("$item ")
                ingGluten += "$item "

            }
            binding.recognizedText.append("\n")
        }

        else {
            val spann = SpannableStringBuilder(" Gluten Free ")
            spann.setSpan(
                ForegroundColorSpan(Color.rgb(0, 135,62)),
                1, // start
                12, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            binding.recognizedText.append("Product is ")
            binding.recognizedText.append(spann)
            binding.recognizedText.append("\n")
        }

        if (meat)
        {
            returnCode+=1
            val spann = SpannableStringBuilder(" NOT Vegetarian ")
            spann.setSpan(
                ForegroundColorSpan(Color.RED),
                1, // start
                15, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            binding.recognizedText.append("Product is ")
            binding.recognizedText.append(spann)
            binding.recognizedText.append("\n")
            ingMeat = ""
            for(item in containMeat)
            {
                binding.recognizedText.append("$item ")
                ingMeat += "$item "
            }
            binding.recognizedText.append("\n")
        }

        else {
            val spann = SpannableStringBuilder(" Vegetarian ")
            spann.setSpan(
                ForegroundColorSpan(Color.rgb(0, 135,62)),
                1, // start
                11, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            binding.recognizedText.append("Product is ")
            binding.recognizedText.append(spann)
            binding.recognizedText.append("\n")
        }


    }
}

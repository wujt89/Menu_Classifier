package com.example.ml_kit_text_recognition

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ImportScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel = ViewModelProvider(this).get(ImportScreenModelView::class.java)
        viewModel.binding = DataBindingUtil.setContentView(this, R.layout.activity_import_screen)

        viewModel.binding.image.setImageResource(viewModel.img_id)
        viewModel.selectedImage = BitmapFactory.decodeResource(resources, viewModel.img_id)
        viewModel.binding.buttonImport.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, viewModel.pickImage)
        }

        viewModel.binding.buttonSwitch.setOnClickListener {
            if(viewModel.flag==0)
            {
                viewModel.flag = 1
                viewModel.binding.image.visibility = View.INVISIBLE
                viewModel.binding.recognizedText.visibility = View.VISIBLE
            }

            else
            {
                viewModel.flag = 0
                viewModel.binding.image.visibility = View.VISIBLE
                viewModel.binding.recognizedText.visibility = View.INVISIBLE
            }
        }

    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var viewModel = ViewModelProvider(this).get(ImportScreenModelView::class.java)
        if (resultCode == RESULT_OK && requestCode == viewModel.pickImage) {
            viewModel.imageUri = data?.data
            viewModel.imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, viewModel.imageUri)
            viewModel.selectedImage = viewModel.imageBitmap
//            val inputImage = InputImage.fromBitmap(viewModel.selectedImage!!, 90)
//            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
//            recognizer.process(inputImage)
//                .addOnSuccessListener {
//                    viewModel.binding.recognizedText.text = it.text
//                }
//                .addOnFailureListener{
//                    Log.d(TAG, "Successful Recognition", it)
//                }
            viewModel.binding.image.visibility = View.VISIBLE
            viewModel.binding.recognizedText.visibility = View.INVISIBLE

            viewModel.startTextRecognition(viewModel.selectedImage!!)

            viewModel.binding.image.setImageBitmap(viewModel.rotateBitmap(viewModel.selectedImage!!, 90f))
        }
    }
}
package com.example.ml_kit_text_recognition

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import java.io.FileDescriptor
import java.io.IOException

class menuScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel = ViewModelProvider(this).get(menuScreenModelView::class.java)
        viewModel.binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_screen)

        viewModel.binding.image.setImageResource(viewModel.img_id)
        viewModel.selectedImage = BitmapFactory.decodeResource(resources, viewModel.img_id)
        viewModel.binding.buttonImport.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, viewModel.pickImage)
        }

        viewModel.binding.buttonTake.setOnClickListener {
            openCamera()
        }

        viewModel.binding.buttonSwitch.setOnClickListener {
            if (viewModel.flag == 0) {
                viewModel.flag = 1
                viewModel.binding.image.visibility = View.INVISIBLE
                viewModel.binding.recognizedText.visibility = View.VISIBLE
            } else {
                viewModel.flag = 0
                viewModel.binding.image.visibility = View.VISIBLE
                viewModel.binding.recognizedText.visibility = View.INVISIBLE
            }
        }

    }

    private fun openCamera() {
        var viewModel = ViewModelProvider(this).get(menuScreenModelView::class.java)
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Take")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Camera")
        viewModel.imageUri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, viewModel.imageUri)
        startActivityForResult(cameraIntent, viewModel.IMAGE_CAPTURE_CODE)
    }

    fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var viewModel = ViewModelProvider(this).get(menuScreenModelView::class.java)
        if (resultCode == RESULT_OK && requestCode == viewModel.pickImage) {
            viewModel.imageUri = data?.data
            viewModel.imageBitmap =
                MediaStore.Images.Media.getBitmap(this.contentResolver, viewModel.imageUri)
            viewModel.selectedImage = viewModel.imageBitmap
            viewModel.binding.image.visibility = View.VISIBLE
            viewModel.binding.recognizedText.visibility = View.INVISIBLE
            viewModel.startTextRecognition(viewModel.selectedImage!!)
            viewModel.binding.image.setImageBitmap(
                viewModel.rotateBitmap(
                    viewModel.selectedImage!!,
                    90f
                )
            )
        }

        if (resultCode == RESULT_OK && requestCode == viewModel.IMAGE_CAPTURE_CODE) {
            viewModel.imageBitmap = uriToBitmap(viewModel.imageUri!!)
            viewModel.selectedImage = viewModel.imageBitmap
            viewModel.binding.image.visibility = View.VISIBLE
            viewModel.binding.recognizedText.visibility = View.INVISIBLE
            viewModel.startTextRecognition(viewModel.selectedImage!!)
            viewModel.binding.image.setImageBitmap(
                viewModel.rotateBitmap(
                    viewModel.selectedImage!!,
                    90f
                )
            )
        }
    }


}
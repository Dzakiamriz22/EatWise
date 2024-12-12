package com.example.eatwise.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.eatwise.R
import com.example.eatwise.activity.ResultActivity
import com.example.eatwise.databinding.FragmentCameraBinding
import com.example.eatwise.network.ApiClient
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment(R.layout.fragment_camera) {
    private val binding by viewBinding(FragmentCameraBinding::bind)

    private lateinit var cameraExecutor: ExecutorService
    private var camera: Camera? = null
    private lateinit var imageCapture: ImageCapture

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startCamera()
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        binding.galleryButton.setOnClickListener {
            openGallery()
        }
        binding.cameraButton.setOnClickListener {
            capturePhoto()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = androidx.camera.core.Preview.Builder().build()
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            } catch (e: Exception) {
                Log.e("CameraFragment", "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun capturePhoto() {
        val photoFile = createFile(requireContext())
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                Log.d("CameraFragment", "Photo captured: $savedUri")
                showProcessingMessage()
                sendImageToApi(savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(requireContext(), "Photo capture failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createFile(context: Context): File {
        val fileName = "JPEG_${System.currentTimeMillis()}.jpg"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        galleryResultLauncher.launch(intent)
    }

    private val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { imageUri ->
                    showProcessingMessage()
                    sendImageToApi(imageUri)
                }
            }
        }

    private fun showProcessingMessage() {
        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Please wait, processing image...", Toast.LENGTH_LONG).show()
        }
    }

    private fun hideProcessingMessage() {
        requireActivity().runOnUiThread {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }

    private fun sendImageToApi(imageUri: Uri) {
        val context = requireContext()
        val file = getFileFromUri(context, imageUri) ?: run {
            Toast.makeText(context, "Invalid image file", Toast.LENGTH_SHORT).show()
            hideProcessingMessage()
            return
        }

        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestBody)

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.uploadImage(multipartBody)

                if (response.isSuccessful) {
                    val predictionResponse = response.body()

                    if (predictionResponse != null) {
                        if (predictionResponse.image_label == "Unknown") {
                            Toast.makeText(
                                context,
                                "No recognizable food item detected.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            val intent = Intent(context, ResultActivity::class.java).apply {
                                putExtra("imageUri", imageUri.toString())
                                putExtra("predictionResponse", Gson().toJson(predictionResponse))
                            }
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(context, "Received empty prediction response.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Failed to upload image: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to send image: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("CameraFragment", "Error sending image", e)
            } finally {
                hideProcessingMessage()
            }
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val fileName = "temp_${System.currentTimeMillis()}.jpg"
        val tempFile = File(context.cacheDir, fileName)

        contentResolver.openInputStream(uri)?.use { inputStream ->
            tempFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return if (tempFile.exists()) tempFile else null
    }
}
package com.hardik.demoapicalling.presentation.ui.image

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.hardik.demoapicalling.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageFragment : Fragment() {
    private val TAG = ImageFragment::class.java.simpleName

    private lateinit var imageView: ImageView
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var requestPermissionLauncher1: ActivityResultLauncher<Array<String>>

    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    private lateinit var photoFile: File
    private lateinit var photoUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.imageView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            initializeActivityResultLaunchers1()
        }else{
            initializeActivityResultLaunchers()
        }
        showImageSourceDialog()
    }

    private fun initializeActivityResultLaunchers() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                openImagePicker() // Open the image picker if permission is granted
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        // Initialize both image pickers and camera launchers
        initializeLaunchers()

//        imagePickerLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                result.data?.data?.let { uri ->
//                    Glide.with(this)
//                        .load(uri)
//                        .into(imageView)
//                    Log.e(TAG, "Selected image URI: $uri")
//                }
//            }
//        }
//
//        cameraLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                // Load the captured image into the ImageView
//                Glide.with(this)
//                    .load(photoUri) // Use the URI we stored
//                    .into(imageView)
//                Log.e(TAG, "Captured image URI: $photoUri")
//            }
//        }
    }

    private fun initializeActivityResultLaunchers1() {
        requestPermissionLauncher1 = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.CAMERA] == true && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && permissions[Manifest.permission.READ_MEDIA_IMAGES] == true || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU && permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) -> {
                    dispatchTakePictureIntent() // Proceed to take picture
                }

                else -> {
                    Toast.makeText(requireContext(), "Permissions denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            //region In Other code
            /*
            val cameraGranted = permissions[Manifest.permission.CAMERA] == true
             val mediaGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                 permissions[Manifest.permission.READ_MEDIA_IMAGES] == true
             } else {
                 permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true
             }

             if (cameraGranted && mediaGranted) {
                 dispatchTakePictureIntent() // Proceed to take picture
             } else {
                 Toast.makeText(requireContext(), "Permissions denied", Toast.LENGTH_SHORT).show()
             }
             */
            //endregion
        }

        // Initialize both image pickers and camera launchers
        initializeLaunchers()

//        imagePickerLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                result.data?.data?.let { uri ->
//                    Glide.with(this)
//                        .load(uri)
//                        .into(imageView)
//                    Log.e(TAG, "Selected image URI: $uri")
//                }
//            }
//        }
//
//        cameraLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                // Load the captured image into the ImageView
//                Glide.with(this)
//                    .load(photoUri) // Use the URI we stored
//                    .into(imageView)
//                Log.e(TAG, "Captured image URI: $photoUri")
//            }
//        }
    }

    private fun initializeLaunchers() {
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            handleImageResult(result, isCamera = false)
        }

        cameraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            handleImageResult(result, isCamera = true)
        }
    }
    private fun handleImageResult(result: ActivityResult, isCamera: Boolean) {
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = if (isCamera) photoUri else result.data?.data
            uri?.let {
                Glide.with(this)
                    .load(it)
                    .into(imageView)
                Log.e(TAG, if (isCamera) "Captured image URI: $it" else "Selected image URI: $it")
            }
        }
    }



    private fun showImageSourceDialog() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Select Image Source")
            .setItems(arrayOf("Camera", "Gallery")) { dialog, which ->
                when (which) {
                    0 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            onCameraSelected1()
                        } else {
                            onCameraSelected()
                        }
                    }

                    1 -> onGallerySelected()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }



    private fun onGallerySelected() {
        checkPermissionsAndOpenPicker()
    }

    private fun checkPermissionsAndOpenPicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
//                requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_MEDIA_IMAGES))

            }
        } else {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openImagePicker()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
//                requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))

            }
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        imagePickerLauncher.launch(intent)
    }



    private fun onCameraSelected() {
        // Check if the CAMERA permission is granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // If CAMERA is granted but WRITE_EXTERNAL_STORAGE is not
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else {
            // Both permissions are granted, proceed to take a picture
            dispatchTakePictureIntent()
        }
    }
    private fun onCameraSelected1() {
        val permissions = mutableListOf<String>()
        permissions.add(Manifest.permission.CAMERA)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        requestPermissionLauncher1.launch(permissions.toTypedArray())
    }

    private fun dispatchTakePictureIntent() {
        // Create an image file
        photoFile = createImageFile()

        // Get the image URI
        photoUri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.fileprovider", photoFile)

        // Launch camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri) // Set the URI for the image
        }
        cameraLauncher.launch(intent)
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)// storage/sdcard0/Pictures/

        // Create a permanent file (not temporary)
        return File(storageDir, "IMG_$timeStamp.jpg").apply {
            Log.d(TAG, "Image file created at: $absolutePath")
        }
//        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)// storage/sdcard0/Android/data/packageName/file
        // Create a Temporary file (not permanent)
//        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
//            Log.d(TAG, "Image file created at: $absolutePath")
//        }
    }
}
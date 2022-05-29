package com.signlingo.main.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.signlingo.R
import com.signlingo.createCustomTempFile
import com.signlingo.databinding.ActivityCameraBinding
import com.signlingo.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CameraActivity : AppCompatActivity() {
    private var _binding: ActivityCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataChar: Array<String>
    private var getFile: File? = null
    private val imageSize = 224

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataChar = resources.getStringArray(R.array.data_char)
        val position = intent.getIntExtra(EXTRA_POSITION, 0)
        val title = getString(R.string.exercise_title, dataChar[position])

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.tvQuizTitle.text = title

        binding.btnCamera.setOnClickListener {
            startTakePhoto()
        }

        binding.btnSubmit.setOnClickListener {
            if(getFile != null){
                val file = getFile as File
                var result = BitmapFactory.decodeFile(file.path)
                val dimension = Math.min(result.width, result.height)
                result = ThumbnailUtils.extractThumbnail(result, dimension, dimension)
                result = Bitmap.createScaledBitmap(result, imageSize, imageSize, false)
                checkHandSign(result, position)
            } else {
                Toast.makeText(this@CameraActivity, "Please add your image first!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkHandSign(image: Bitmap, position: Int){
        val model = Model.newInstance(applicationContext)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0

        for(i in 0 until imageSize){
            for(j in 0 until imageSize){
                val value = intValues[pixel++]
                byteBuffer.putFloat((value shr 16 and 0xFF) * (1f / 255))
                byteBuffer.putFloat((value shr 8 and 0xFF) * (1f / 255))
                byteBuffer.putFloat((value and 0xFF) * (1f / 255))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        var maxPos = 0
        var maxConfidence = 0f
        for(i in confidences.indices){
            if(confidences[i] > maxConfidence){
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        val labels = arrayOf("A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W",
            "X", "Y", "Z")

        if(labels[maxPos] == dataChar[position]){
            val intent = Intent(this@CameraActivity, TrueActivity::class.java)
            intent.putExtra(TrueActivity.EXTRA_POSITION, position)
            startActivity(intent)
        } else {
            val intent = Intent(this@CameraActivity, FalseActivity::class.java)
            startActivity(intent)
        }

        model.close()
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            var result = BitmapFactory.decodeFile(myFile.path)
            val dimension = Math.min(result.width, result.height)
            result = ThumbnailUtils.extractThumbnail(result, dimension, dimension)
            binding.imgResult.setImageBitmap(result)
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@CameraActivity,
                "com.signlingo",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Failed to get camera permission!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val EXTRA_POSITION = "extra_position"
    }
}
package co.id.signaturedragable.ui.signature

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import co.id.signaturedragable.R
import co.id.signaturedragable.utils.showToast
import kotlinx.android.synthetic.main.activity_insert_signature.*
import java.io.*

class CreateSignatureActivity : AppCompatActivity() {

    private val REQUEST_CODE_CAMERA_PERMISSION = 1072


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_signature)
        requestCameraPermission()

        btn_save_signature.setOnClickListener {
            saveToInternalStorage(this, cv_signature.extraBitmap)
        }

    }

    private fun requestCameraPermission() {
        if (!isCameraPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA_PERMISSION
            )
        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun saveToInternalStorage(context: Context, bitmapImage: Bitmap) {

        // Create imageDir
        val mypath = File(this.cacheDir, "preview.jpg")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        context.showToast("Saved Signature")
        finish()
    }
}
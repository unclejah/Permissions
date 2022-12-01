package com.example.permissionssamples

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.example.permissionssamples.ext.checkSelfPermissionCompat
import com.example.permissionssamples.ext.requestPermissionsCompat
import com.example.permissionssamples.ext.shouldShowRequestPermissionRationaleCompat

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    companion object{
        const val PERMISSION_REQUEST_CAMERA = 0
    }

    private val btnCamera: Button by lazy {findViewById(R.id.btnCamera)}
    private val layout: ConstraintLayout by lazy { findViewById(R.id.mainLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCamera.setOnClickListener {
            showCameraPreview()
        }
    }

    private fun showCameraPreview() {

        if (checkSelfPermissionCompat(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {

            startCamera()
            Toast.makeText (this, "Разрешение предоставлено", Toast.LENGTH_LONG).show()
        } else {

            requestCameraPermission()
        }
    }


    private fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.CAMERA)){
            Toast.makeText (this, "Разрешение не предоставлено", Toast.LENGTH_LONG).show()

        } else {

            Toast.makeText (this, "Разрешение не предоставлено", Toast.LENGTH_LONG).show()
            requestPermissionsCompat(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        }
    }

    private fun startCamera () {
        Log.d("Debug", "CAMERA OPENED")
        val intent = Intent(this, CameraPreviewActivity::class.java)
        startActivity(intent)

    }

    private fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts(
            "package",
            packageName, null
        )
        intent.setData (uri)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode== PERMISSION_REQUEST_CAMERA) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение предоставлено", Toast.LENGTH_LONG).show()
                requestPermissionsCompat(
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CAMERA
                )
                startCamera()
            } else {

                Toast.makeText(this, "Разрешение может быть изменено только в настройках",
                    Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
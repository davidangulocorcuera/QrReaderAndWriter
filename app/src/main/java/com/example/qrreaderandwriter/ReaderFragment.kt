package com.example.qrreaderandwriter


import android.Manifest
import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_reader.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ReaderFragment : Fragment(), ZXingScannerView.ResultHandler {
    lateinit var scanner: ZXingScannerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scanner = ZXingScannerView(context)
        return scanner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()){
            scanner = ZXingScannerView(context)

        }
        else{
            requestPermission()
        }
    }

    fun checkPermissions(): Boolean{
        return (context?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.CAMERA) }
                == PackageManager.PERMISSION_GRANTED)

    }



    fun requestPermission(){
        this.activity?.let { ActivityCompat.requestPermissions(it, arrayOf(CAMERA), REQUEST_CAMERA) }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else Toast.makeText(context, "sin permisos", Toast.LENGTH_SHORT).show()
        }
    }


    override fun handleResult(result: Result?) {
        tvBarcode.text = result.toString()
        scanner.resumeCameraPreview(this)
    }


    override fun onPause() {
        super.onPause()
        scanner.stopCamera()
    }
    companion object {
        const val REQUEST_CAMERA = 1
    }


}

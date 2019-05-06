package com.example.qrreaderandwriter


import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_reader.view.*


class ReaderFragment : Fragment() {

    var mutableUrl = MutableLiveData<String>()
    lateinit var observerUrl: Observer<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reader, container, false).also { view ->
             observerUrl =  object: Observer<String> {
                override fun onChanged(url: String?) {
                    view.tvBarcode.text = url
                }
            }
            mutableUrl.observe(this, observerUrl)

            view.btnScan.setOnClickListener {
                if (checkPermissions()) {
                    initializeBarcodeScan()
                } else {
                    requestPermission()
                }
            }

        }
    }


    private fun initializeBarcodeScan() {
        val integrator = IntentIntegrator(activity)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
        integrator.setPrompt("Apunta al qr")
        integrator.setOrientationLocked(true)
        integrator.setCameraId(0)
        integrator.initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
        mutableUrl.postValue(scanResult.contents.toString())
    }


    private fun checkPermissions(): Boolean {
        return (context?.let { ContextCompat.checkSelfPermission(it, CAMERA) }
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
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

    companion object {
        const val REQUEST_CAMERA = 1
    }


}

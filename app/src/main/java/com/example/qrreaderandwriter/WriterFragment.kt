package com.example.qrreaderandwriter


import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.fragment_writer.view.*


class WriterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_writer, container, false).apply {
            this.btnCreate.setOnClickListener {
                ivQrImage.setImageBitmap(encodeAsBitmap(etUrl.text.toString()))
            }
            this.btnRead.setOnClickListener {
                showReaderFragment()
            }

        }
    }


    @Throws(WriterException::class)
    fun encodeAsBitmap(str: String): Bitmap? {
        val result: BitMatrix
        try {
            result = MultiFormatWriter().encode(
                str,
                BarcodeFormat.QR_CODE, 400, 400, null
            )
        } catch (iae: IllegalArgumentException) {
            // Unsupported format
            return null
        }
        val width = result.width
        val height = result.height
        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (result.get(x, y)) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, 400, 0, 0, width, height)
        return bitmap
    }

    private fun showReaderFragment() {
        fragmentManager!!.beginTransaction().apply {
            this.replace(R.id.container, ReaderFragment())
            this.commit()
        }

    }

}

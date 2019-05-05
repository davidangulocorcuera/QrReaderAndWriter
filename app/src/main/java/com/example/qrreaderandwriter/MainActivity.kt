package com.example.qrreaderandwriter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showWriterFragment()

    }

    private fun showWriterFragment() {
        supportFragmentManager.beginTransaction().apply {
            this.replace(R.id.container, WriterFragment())
            this.commit()
        }

    }
}

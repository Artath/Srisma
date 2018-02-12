package com.example.srisma

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        colorBtn.setOnClickListener({
            var intent = Intent(applicationContext, ColorizationActivity::class.java)
            startActivity(intent)
        })
        neuralBtn.setOnClickListener({
            var intent = Intent(applicationContext, NeuralStyleActivity::class.java)
            startActivity(intent)
        })
    }
}

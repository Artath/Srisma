package com.example.srisma

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_colorization.*
import java.io.File

class ColorizationActivity : AppCompatActivity() {

    private var IMAGE_PATH: String? = null
    private var OUTPUT_IMAGE : String = ""
//testbranch comit 2



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colorization)

        IMAGE_PATH = null

        putImageBtn.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        })
        colorBtn.setOnClickListener(View.OnClickListener {
            colorize()
        })
        saveBtn.setOnClickListener(View.OnClickListener {
            saveImage()
        })

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK && null != data){
            val img_path = data.getData()
            Picasso.with(applicationContext).load(img_path).into(chosenView)
            IMAGE_PATH = FileChooser.getPath(applicationContext, img_path)
            saveBtn.visibility = View.GONE

        }


    }

    private fun colorize() {
        if( IMAGE_PATH != null ) {
            val URL: String = "https://api.deepai.org/api/colorizer"
            FuelManager.instance.baseHeaders = mapOf("Api-Key" to "d11c457f-3a68-4197-8687-2ca318caa8bf")
            Fuel.upload(URL).source{ request, url -> File(IMAGE_PATH) }.name{"image"}.responseString { request, response, result ->
                when(result) {
                    is Result.Success -> { response(true, result.get(), response) }
                    is Result.Failure -> { response(false, result.get(), response) }
                }
            }
        } else {
            Toast.makeText(applicationContext, "Choose image first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun response(succes: Boolean, result: String, response: Response) {
        if( !succes ) {
            Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
        } else {
            updateOutput( Gson().fromJson(result, ImgUrl::class.java) )
        }
    }

    private fun updateOutput(img: ImgUrl) {
        OUTPUT_IMAGE = img.getURL()
        Picasso.with(applicationContext).load(img.getURL()).into(processedImg, object: Callback {
            override fun onSuccess() { updateCallback() }
            override fun onError() { updateCallback() }
        })
    }

    fun updateCallback() {
        saveBtn.visibility = View.VISIBLE
    }

    private fun saveImage() {
        val names = OUTPUT_IMAGE.split("/")
        val name = names[names.size - 1]
        Picasso.with(applicationContext).load(IMAGE_PATH).into(SaveImg(applicationContext, name))
    }

}

package com.example.srisma

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.widget.Toast
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File
import java.io.FileOutputStream

class SaveImg(var ctx: Context, var img: String): Target {
    private lateinit var name: String

    override fun onBitmapFailed(errorDrawable: Drawable?) {
    }

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
        name = img
        val file = File(Environment.getExternalStorageDirectory().getPath()+"/Pictures/"+name)
        try {
            file.createNewFile()
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, out)
            out.close()
            Toast.makeText(ctx, "Image %s saved".format(name), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

    }
}
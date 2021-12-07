package com.example.thewitcherrpg.feature_character_sheet.domain.use_cases.character_information

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.thewitcherrpg.core.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    private val application: Application
) {

    operator fun invoke(uri: Uri, id: Int): Flow<Resource<String>> = flow {
        val cw = ContextWrapper(application.applicationContext)
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)

        val myPath = File(directory, "$id.jpeg")
        var fos: FileOutputStream? = null


        try {
            fos = FileOutputStream(myPath)

            // Use the compress method on the BitMap object to write image to the OutputStream
            getContent(uri)?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            emit(Resource.Success(myPath.absolutePath))

        } catch (e: Exception) {
            emit(Resource.Error("Unexpected Error", e.toString()))
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Unexpected Error", e.toString()))
            }
        }

        //return directory.absolutePath
    }

    fun getContent(uri: Uri): Bitmap?{

        try {
            val bitmap: Bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(
                    application.contentResolver, uri
                )
            } else {
                val source = ImageDecoder.createSource(application.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
            return bitmap
        }
        catch (e: NullPointerException){
            Log.e("Error", e.toString())
        }
        return null
    }
}
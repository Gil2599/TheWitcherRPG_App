package com.example.thewitcherrpg.feature_character_sheet.presentation.character_information.domain.use_cases

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import com.example.thewitcherrpg.core.Resource
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

    operator fun invoke(bitmapImage: Bitmap, id: Int): Flow<Resource<String>> = flow{
        val cw = ContextWrapper(application.applicationContext)
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)

        val myPath = File(directory, "$id.jpeg")
        var fos: FileOutputStream? = null

        withContext(Dispatchers.IO) {
            try {

                fos = FileOutputStream(myPath)

                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                emit(Resource.Success(directory.absolutePath))

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
        }
        //return directory.absolutePath
    }
}
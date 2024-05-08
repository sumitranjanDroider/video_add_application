package com.sumitranjan.videoaddproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.sumitranjan.videoaddproject.Common.TAG_MAIN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
class DiscCatch(val context: Context){
    suspend fun save(uri: String,fileName: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        if (uri.isEmpty()) {
            Log.e(TAG_MAIN, "No URI provided")
            return@withContext Pair(false, "")
        }

        val cacheDir = context.cacheDir
       // val fileName = //uri.substringAfterLast("/")

        try {
            val urlConnection = java.net.URL(uri).openConnection()
            urlConnection.connect()

            // Input stream to read the data
            val inputStream = BufferedInputStream(urlConnection.getInputStream())

            // Output stream to write the data
            val outputFile = File(cacheDir, fileName)
            val outputStream = FileOutputStream(outputFile)
            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            // Close streams
            outputStream.flush()
            outputStream.close()
            inputStream.close()

            Log.d(TAG_MAIN, "File saved to cache: $fileName")
            return@withContext Pair(true, fileName)
        } catch (e: IOException) {
            Log.e(TAG_MAIN, "Error saving file to cache", e)
            return@withContext Pair(false, "")
        }
    }

    suspend fun get(fileName: String): File? = withContext(Dispatchers.IO) {
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)

        if (file.exists()) {
            return@withContext file
        } else {
            Log.e(TAG_MAIN, "File not found: $fileName")
            return@withContext null
        }
    }
}
/*
object VideoCatchManager{
    suspend fun saveVideoToCache(context: Context, videoByteArray: ByteArray, fileName: String) = withContext(Dispatchers.IO){
        val cacheDir = context.cacheDir // Use internal cache directory
        val file = File(cacheDir, fileName)

        try {
            val fos = FileOutputStream(file)
            fos.write(videoByteArray)
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    suspend fun getVideoFileFromCache(context: Context, fileName: String): File? = withContext(Dispatchers.IO){
       // val cacheDir = context.cacheDir // Use internal cache directory
        //return File(cacheDir, fileName)
        val cacheDir = context.cacheDir
        val file = File(cacheDir, fileName)
        if (file.exists()) {
            return@withContext File(file.absolutePath)
        }
        return@withContext null
    }
    suspend fun uriToByteArray(uri: Uri): ByteArray? = withContext(Dispatchers.IO){
        val connection=URL(uri.toString()).openConnection() as HttpURLConnection
        connection.doInput=true
        connection.connect()
        val inputStream: InputStream? = connection.inputStream
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)

        inputStream?.use { input ->
            var len: Int
            while (input.read(buffer).also { len = it } != -1) {
                byteBuffer.write(buffer, 0, len)
            }
        }
        return@withContext byteBuffer.toByteArray()
    }
}*/

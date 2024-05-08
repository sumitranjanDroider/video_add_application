package com.sumitranjan.videoaddproject

import android.util.Log
import com.sumitranjan.videoaddproject.Common.TAG_MAIN
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

object InternetConnectionHave {
    // Make sure to execute this on a background thread.
    fun execute(socketFactory: SocketFactory): Boolean {
        return try{
            Log.d(TAG_MAIN, "PINGING google.")
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("https://google.com",53), 1500)
            socket.close()
            Log.d(TAG_MAIN, "PING success.")
            true
        }catch (e: IOException){
            Log.e(TAG_MAIN, "No internet connection. ${e}")
            false
        }
    }
}
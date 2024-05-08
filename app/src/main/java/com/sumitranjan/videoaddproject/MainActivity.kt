package com.sumitranjan.videoaddproject

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.sumitranjan.videoaddproject.Common.TAG_MAIN

import com.sumitranjan.videoaddproject.databinding.ActivityMainBinding
import com.sumitranjan.videoaddproject.viewModel.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var connectionLiveDate:ConnectionLiveData
    private lateinit var binding:ActivityMainBinding
    private lateinit var player: ExoPlayer

   private val viewModel: MainActivityViewModel by viewModels<MainActivityViewModel> ()

    private lateinit var discCatch: DiscCatch
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveDate=ConnectionLiveData(this)

        enableEdgeToEdge()
        discCatch=DiscCatch(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.navigationBars() or WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            window.decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.hide()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)


        binding.apply {
            player=ExoPlayer.Builder(this@MainActivity).build()
            viewPlayer.player=player
            viewPlayer.useController=false
            player.prepare()
            player.repeatMode=Player.REPEAT_MODE_ALL
            player.play()
            //Url List observer will observe add list items
            viewModel.videoAddList.observe(this@MainActivity){
                CoroutineScope(Dispatchers.IO).launch {
                    val fileList= mutableListOf<MediaItem>()
                    for (add in it){
                        discCatch.save(add.url,add.fileName)
                        fileList.add(MediaItem.fromUri(discCatch.get(add.fileName)!!.toUri()))
                    }
                    withContext(Dispatchers.Main){
                        viewModel.mediaSourceFileList.postValue(fileList)
                    }
                }

            }
            viewModel.mediaSourceFileList.observe(this@MainActivity){
                player.setMediaItems(it)
            }
            viewModel.internetAvailable.observe(this@MainActivity){
                if (it){
                    viewModel.arrangeAddList()
                    Log.d(TAG_MAIN,"internet connected refresh data")
                }
            }
        }
        connectionLiveDate.observe(this@MainActivity) { isNetworkAvailable ->
              if (isNetworkAvailable){
                  viewModel.arrangeAddList()
              }else{
                  Log.d(TAG_MAIN,"internet disconnected")
              }
        }
    }





}
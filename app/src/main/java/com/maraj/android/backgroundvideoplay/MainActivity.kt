package com.maraj.android.backgroundvideoplay

import android.content.res.AssetFileDescriptor
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.media.AsyncPlayer
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.Surface
import android.view.TextureView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import android.media.MediaMetadataRetriever
import java.io.IOException


class MainActivity : AppCompatActivity(), TextureView.SurfaceTextureListener {

    var mediaPlayer: MediaPlayer? = null
    lateinit var assetFileDescriptor: AssetFileDescriptor


    override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture?, width: Int, height: Int) {
        Log.e("MARAJ ", "onSurfaceTextureSizeChanged")
        try {
            assetFileDescriptor = assets.openFd("video_file.mp4")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val surface = Surface(surfaceTexture)
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            mediaPlayer?.prepareAsync()
            mediaPlayer?.setSurface(surface)
            mediaPlayer?.isLooping = true
            mediaPlayer?.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
                override fun onPrepared(player: MediaPlayer?) {
                    Log.e("MARAJ ", "setOnPreparedListener ")
                    player?.start()
                }

            })
            mediaPlayer?.setOnErrorListener(object : MediaPlayer.OnErrorListener {
                override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
                    Log.e("MARAJ ", "setOnErrorListener")
                    return false
                }

            })


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {
        Log.e("MARAJ ", "onSurfaceTextureUpdated")
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean {
        Log.e("MARAJ ", "onSurfaceTextureDestroyed")
        return false
    }

    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {
        Log.e("MARAJ ", "onSurfaceTextureAvailable")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textureView1.surfaceTextureListener = this@MainActivity
        Log.e("MARAJ ", "----------->>>")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}

package com.sunexample.demoforandroidxandkotlin.VideoTest


import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import com.sunexample.demoforandroidxandkotlin.R
import kotlinx.android.synthetic.main.activity_video_test.*


class VideoTestActivity : AppCompatActivity() {

    private var num = 0
    private val TAG = "VideoTestActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_test)



        countview.setDuration(30, object : SunBottomCounterView.CountStatus {
            override fun start() {
                Log.d(TAG, "start")
            }

            override fun pause() {
                Log.d(TAG, "pause")
            }

            override fun finish() {
                Log.d(TAG, "finish")
            }

            override fun second(current: Int) {
                Log.d(TAG, "second : ${current}")
            }
        })


        btn_test.setOnClickListener {
            countview.startorpause()
        }


        circircountview.setDuration(30, 25, object : SunCircleCounter.CircleCountStatus {
            override fun start() {

            }

            override fun cancel() {
            }

            override fun finish() {
            }

            override fun addtime() {

            }

            override fun second(current: Int) {
                Log.d(TAG, "second : ${current}")
            }

        })



        btn_test2.setOnClickListener {
            circircountview.startAndroidpause()
        }

        btn_add_time.setOnClickListener {
            circircountview.addTime()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        countview.release()
        circircountview.release()
    }

//    private fun initPLayer() {
//        val player = SimpleExoPlayer.Builder(this).build()
//        playerview.player = player
//        playerview.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
//        // Build the media items.
//        val mediaSource = buildRawMediaSource()
//        player.repeatMode = Player.REPEAT_MODE_ALL
//        player.addMediaSource(mediaSource!!)
//        player.prepare()
//        player.play()
//    }

//    private fun buildRawMediaSource(): MediaSource? {
//        val rawDataSource = RawResourceDataSource(this)
//        // open the /raw resource file
//        rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.kaihetiao01)))
////        rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.kaihetiao02)))
//
//        // Create media Item
//        val mediaItem1 = MediaItem.fromUri(rawDataSource.uri!!)
//        val mediaItem2 = MediaItem.fromUri(rawDataSource.uri!!)
//
//        // create a media source with the raw DataSource
//        val mediaSource = ProgressiveMediaSource.Factory { rawDataSource }
//            .createMediaSource(mediaItem1)
//
//        return mediaSource
//    }

    private fun TTSTest() {
        val systemTTS = SystemTTS.getInstance(this)

        Thread(Runnable {
            while (true) {
                Thread.sleep(1500)
                num++
                runOnUiThread {
                    systemTTS.playText(num.toString())
                }
            }
        }).start()
    }


}
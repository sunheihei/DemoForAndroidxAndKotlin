package com.sunexample.demoforandroidxandkotlin.FitnessTest


import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Log
import com.sunexample.demoforandroidxandkotlin.R
import kotlinx.android.synthetic.main.activity_video_test.*
import java.io.File

/**
 * FitNess 健身应用测试
 */

class VideoTestActivity : AppCompatActivity() {


    private val TAG = "VideoTestActivity";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_test)

        inittts()

    }

    private fun inittts() {
        save_wav.setOnClickListener {
            SaveWav()
        }
    }


    private fun initCountView() {

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


        circircountview.setOnClickListener {
            circircountview.addTime()
        }

        circircountview.setDuration(30, 25, object : SunCircleCounter.CircleCountStatus {
            override fun start() {
                Log.d(TAG, "start")
            }

            override fun cancel() {
                Log.d(TAG, "cancel")
            }

            override fun finish() {
                Log.d(TAG, "finish")
            }

            override fun addtime() {
                Log.d(TAG, "addtime")
            }

            override fun second(current: Int) {
                Log.d(TAG, "second : ${current}")
            }

        })


        skip.setOnClickListener {
            circircountview.release()
        }

        btn_test2.setOnClickListener {
            circircountview.startAndroidpause()
        }

        btn_add_time.setOnClickListener {
            circircountview.addTime()
        }
    }


    private fun SaveWav() {


        val input:CharSequence = "First Exercise,Arm_Circles,20 seconds,3,2,1,go!"
        val CacheFile =
            "${getExternalFilesDir(Environment.DIRECTORY_MUSIC)}${File.separator}TempTTSWav.wav"

        Log.d(TAG, "${CacheFile}");
        val tempWavFile = File(CacheFile)

        if (tempWavFile.exists()){
            tempWavFile.delete()
        }

        TTSUtils.wavToLocal(input, SystemTTS.getInstance().textToSpeech, tempWavFile)
    }


    override fun onDestroy() {
        super.onDestroy()
        countview.release()
    }


    private fun initPLayer() {
        val player = SimpleExoPlayer.Builder(this).build()
        playerview.player = player
        playerview.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
        // Build the media items.
        val mediaSource = buildRawMediaSource()
        player.repeatMode = Player.REPEAT_MODE_ALL
        player.addMediaSource(mediaSource!!)
        player.prepare()
        player.play()
    }

    private fun buildRawMediaSource(): MediaSource? {
        val rawDataSource = RawResourceDataSource(this)
        // open the /raw resource file
        rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.jumping_jack1)))
//        rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.kaihetiao02)))

        // Create media Item
        val mediaItem1 = MediaItem.fromUri(rawDataSource.uri!!)
        val mediaItem2 = MediaItem.fromUri(rawDataSource.uri!!)

        // create a media source with the raw DataSource
        val mediaSource = ProgressiveMediaSource.Factory { rawDataSource }
            .createMediaSource(mediaItem1)

        return mediaSource
    }


}
package com.sunexample.demoforandroidxandkotlin.ReadAndWrite

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX
import com.sunexample.demoforandroidxandkotlin.R
import kotlinx.android.synthetic.main.activity_read_and_write.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ReadAndWriteActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "ReadAndWriteActivity"

    /**
     * 官方文档地址
     * https://developer.android.com/training/data-storage/app-specific#internal
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_and_write)

        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_MEDIA_LOCATION
            )
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        val volumeNames: Set<String> =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                MediaStore.getExternalVolumeNames(this)
            } else {
                TODO("VERSION.SDK_INT < Q")
            }
        val firstVolumeName = volumeNames.iterator().next()

        Log.d(TAG, "firstVolumeName:$firstVolumeName")


        btn_write_cache_file.setOnClickListener(this)
        btn_write_out_cache_file.setOnClickListener(this)
        btn_write_ex_file.setOnClickListener(this)
        btn_read_cache_file.setOnClickListener(this)
        btn_read_out_cache_file.setOnClickListener(this)
        btn_read_ex_file.setOnClickListener(this)

    }


    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_write_cache_file -> {
                WriteCacheFile()
            }
            R.id.btn_read_cache_file -> {
                ReadCacheFile()
            }
            R.id.btn_write_out_cache_file -> {
                WriteOutCacheFile()
            }
            R.id.btn_read_out_cache_file -> {
                ReadOutCacheFile()
            }
            R.id.btn_write_ex_file -> {
                WriteExtraFile()
            }
            R.id.btn_read_ex_file -> {
            }
        }
    }


    //写入内部存储
    private fun WriteCacheFile() {
        val filename = "myfile"
        val fileContents = "Hello world!"
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
        Log.d(TAG, " WriteCacheFile  Succecc")
    }

    //读取内部存储
    private fun ReadCacheFile() {
        val filename = "myfile"
        var Content: String = ""
        openFileInput(filename).bufferedReader().use {
            Content = it.readText()
        }
        Log.d(TAG, " ReadCacheFile  Succecc! Content: $Content")
    }


    private fun WriteOutCacheFile() {
        val filename = "myfile"
        val fileContents = "Hello world!"
        if (isExternalStorageWritable()) {
            val CacheFile =
                File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename)

//            Log.d(TAG, "appSpecificExternalDir:$CacheFile")

            FileOutputStream(CacheFile).use {
                it.write(fileContents.toByteArray())
            }
            Log.d(TAG, " WriteCacheFile  Succecc")
        }
    }


    private fun ReadOutCacheFile() {
        val filename = "myfile"
        var Content: String = ""
        if (isExternalStorageWritable()) {
            val CacheFile =
                File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename)
            FileInputStream(CacheFile).bufferedReader().use {
                Content = it.readText()
            }
        }
        Log.d(TAG, " ReadCacheFile  Succecc! Content: $Content")
    }

    //写入外部存储
    private fun WriteExtraFile() {
        val sdDir = Environment.getExternalStorageDirectory().absoluteFile //获取跟目录
        Log.d(TAG, "sdDir : $sdDir")
    }

    //读取外部存储
    private fun ReadExtraFile() {


    }


    companion object {
        const val TAG = "ReadAndWriteActivity"
    }


}
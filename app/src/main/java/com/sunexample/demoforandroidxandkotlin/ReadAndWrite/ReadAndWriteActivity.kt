package com.sunexample.demoforandroidxandkotlin.ReadAndWrite

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX
import com.sunexample.demoforandroidxandkotlin.R
import kotlinx.android.synthetic.main.activity_read_and_write.*
import java.io.*
import java.net.URI


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
        btn_create_ex_new_file.setOnClickListener(this)
        btn_delete_ex_file.setOnClickListener(this)

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
            R.id.btn_create_ex_new_file -> {
                WriteToExtraDownLoad()
            }
            R.id.btn_delete_ex_file -> {
                deleteExFile()
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

    }

    //读取外部存储
    private fun ReadExtraFile() {

    }


    var Uri: Uri? = null

    //写入外部存储
    private fun WriteToExtraDownLoad() {
        //insertFileIntoMediaStore 方法只能在文件没生成的情况下生效，当文件已经存在，则uri会返回null
        var uri = insertFileIntoMediaStore(
            "myfile.txt",
            "",
            Environment.DIRECTORY_DOWNLOADS
//            Environment.DIRECTORY_DOWNLOADS + File.separator + "DemoApp"  //中间多创建一层文件夹

        )
        uri?.let {
            Uri = it
        }
        Log.d(TAG, " uri1: $uri")
        saveFile(this, uri)
    }

    //删除外部存储
    private fun deleteExFile() {
        contentResolver.delete(Uri!!, null, null)
        Log.d(TAG, " Delete File  Succecc")
    }


    private fun saveFile(context: Context, insertUri: Uri?) {
        if (insertUri == null) {
            return
        }
        val fileContents = "Hello world!"
        if (isExternalStorageWritable()) {
            getContentResolver().openOutputStream(insertUri).use {
                it!!.write(fileContents.toByteArray())
            }
            Log.d(TAG, " WriteCacheFile  Succecc")
        }
    }


    private fun insertFileIntoMediaStore(
        fileName: String,
        MIME_TYPE: String,
        relativePath: String
    ): Uri? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return null
        }
        val resolver: ContentResolver = getContentResolver()
        //设置文件参数到ContentValues中
        val values = ContentValues()
        //设置文件名
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName)
        //设置文件描述，这里以文件名为例子
        values.put(MediaStore.Downloads.DOCUMENT_ID, fileName)
        //设置文件类型
        values.put(MediaStore.Downloads.MIME_TYPE, "text/plain")
        //注意RELATIVE_PATH需要targetVersion=29
        //故该方法只可在Android10的手机上执行
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //android Q中不再使用DATA字段，而用RELATIVE_PATH代替
            //RELATIVE_PATH是相对路径不是绝对路径
            //DCIM是系统文件夹，关于系统文件夹可以到系统自带的文件管理器中查看，不可以写没存在的名字
            values.put(MediaStore.Downloads.RELATIVE_PATH, relativePath)
            //contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Music/signImage");
        } else {
            values.put(
                MediaStore.Images.Media.DATA,
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .getPath()
            );
        }
        //EXTERNAL_CONTENT_URI代表外部存储器
        val external = MediaStore.Downloads.EXTERNAL_CONTENT_URI
        //insertUri表示文件保存的uri路径
        return resolver.insert(external, values)
    }

    companion object {
        const val TAG = "ReadAndWriteActivity"
    }


}
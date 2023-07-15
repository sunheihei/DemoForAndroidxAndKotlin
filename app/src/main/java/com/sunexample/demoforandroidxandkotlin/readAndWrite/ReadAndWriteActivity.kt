package com.sunexample.demoforandroidxandkotlin.readAndWrite

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityReadAndWriteBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class ReadAndWriteActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "ReadAndWriteActivity"

    lateinit var binding: ActivityReadAndWriteBinding

    /**
     * 官方文档地址
     * https://developer.android.com/training/data-storage/app-specific#internal
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadAndWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        PermissionX.init(this).permissions(
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION
        ).request { allGranted, grantedList, deniedList ->
            if (allGranted) {
                Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    this, "These permissions are denied: $deniedList", Toast.LENGTH_LONG
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


        binding.btnWriteCacheFile.setOnClickListener(this)
        binding.btnWriteOutCacheFile.setOnClickListener(this)
        binding.btnWriteExFile.setOnClickListener(this)
        binding.btnReadCacheFile.setOnClickListener(this)
        binding.btnReadOutCacheFile.setOnClickListener(this)
        binding.btnReadExFile.setOnClickListener(this)
        binding.btnCreateExNewFile.setOnClickListener(this)
        binding.btnDeleteExFile.setOnClickListener(this)
        binding.btnCreateExDoc.setOnClickListener(this)
        binding.btnOpenExDoc.setOnClickListener(this)
        binding.btnOpenDocDirectory.setOnClickListener(this)

    }


    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_write_cache_file -> {
                writeCacheFile()
            }

            R.id.btn_read_cache_file -> {
                readCacheFile()
            }

            R.id.btn_write_out_cache_file -> {
                writeOutCacheFile()
            }

            R.id.btn_read_out_cache_file -> {
                readOutCacheFile()
            }

            R.id.btn_write_ex_file -> {
                writeExtraFile()
            }

            R.id.btn_read_ex_file -> {
                readExtraFile()
            }

            R.id.btn_create_ex_new_file -> {
                writeToExtraDownLoad()
            }

            R.id.btn_delete_ex_file -> {
                deleteExFile()
            }

            R.id.btn_create_ex_doc -> {
                createFile(null);
            }

            R.id.btn_open_ex_doc -> {
                openFile(null)
            }

            R.id.btn_open_doc_directory -> {
                openDirectory(null)
            }
        }
    }


    //写入内部存储
    private fun writeCacheFile() {
        val filename = "myfile"
        val fileContents = "Hello world!"
        openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
        Log.d(TAG, " WriteCacheFile  Succecc")
    }

    //读取内部存储
    private fun readCacheFile() {
        val filename = "myfile"
        var Content: String = ""
        openFileInput(filename).bufferedReader().use {
            Content = it.readText()
        }
        Log.d(TAG, " ReadCacheFile  Succecc! Content: $Content")
    }


    private fun writeOutCacheFile() {
        val filename = "myfile"
        val fileContents = "Hello world!"
        if (isExternalStorageWritable()) {
            val CacheFile = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename)

//            Log.d(TAG, "appSpecificExternalDir:$CacheFile")

            FileOutputStream(CacheFile).use {
                it.write(fileContents.toByteArray())
            }
            Log.d(TAG, " WriteCacheFile  Succecc")
        }
    }


    private fun readOutCacheFile() {
        val filename = "myfile"
        var Content: String = ""
        if (isExternalStorageWritable()) {
            val CacheFile = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), filename)
            FileInputStream(CacheFile).bufferedReader().use {
                Content = it.readText()
            }
        }
        Log.d(TAG, " ReadCacheFile  Succecc! Content: $Content")
    }

    //写入外部存储
    private fun writeExtraFile() {

    }

    //读取外部存储
    private fun readExtraFile() {
        var Content: String = ""
        contentResolver.openInputStream(Uri!!).use { stream ->
            stream!!.bufferedReader().use {
                Content = it.readText()
            }
            // Perform operations on "stream".
        }
        Log.d(TAG, " ReadFile  Succecc! Content: $Content")
    }


    var Uri: Uri? = null

    //写入外部存储
    private fun writeToExtraDownLoad() {
        //insertFileIntoMediaStore 方法只能在文件没生成的情况下生效，当文件已经存在，则uri会返回null
        var uri = insertFileIntoMediaStore(
            "myfile.txt", "", Environment.DIRECTORY_DOWNLOADS
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

    private val CREATE_FILE = 1

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createFile(pickerInitialUri: Uri?) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_TITLE, "invoice.pdf")

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
//        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        startActivityForResult(intent, CREATE_FILE)
    }

    val PICK_PDF_FILE = 2
    private fun openFile(pickerInitialUri: Uri?) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"

            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
//            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }
        startActivityForResult(intent, PICK_PDF_FILE)
    }

    val REQUEST_CODE = 3
    fun openDirectory(pickerInitialUri: Uri?) {
        // Choose a directory using the system's file picker.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        startActivityForResult(intent, REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                Log.d(TAG, " PICK_PDF_FILE uri:${uri}")
            }
        } else if (requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                saveFile(this, uri)
                Log.d(TAG, "CREATE_FILE uri:${uri}")
            }
        }
    }


    private fun saveFile(context: Context, insertUri: Uri?) {
        if (insertUri == null) {
            return
        }
        val fileContents = "Hello world!"
        if (isExternalStorageWritable()) {
            contentResolver.openOutputStream(insertUri).use {
                it!!.write(fileContents.toByteArray())
            }
            Log.d(TAG, " WriteCacheFile  Succecc")
        }
    }


    private fun insertFileIntoMediaStore(
        fileName: String, MIME_TYPE: String, relativePath: String
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
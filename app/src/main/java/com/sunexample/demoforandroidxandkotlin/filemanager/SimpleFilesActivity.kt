package com.sunexample.demoforandroidxandkotlin.filemanager

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.sunexample.demoforandroidxandkotlin.BuildConfig
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.databinding.ActivitySimpleFilesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class SimpleFilesActivity : AppCompatActivity() {

    private val TAG = "SimpleFilesActivity"

    lateinit var binding: ActivitySimpleFilesBinding

    private val adapter = StorageAdapter()

    //根目录
    private lateinit var mExternalFile: File

    lateinit var tempFilePath: String

    private val dataPath = "/storage/emulated/0/Android/data"

    val REQUEST_GRANT_DATA = 0

    //当前文件夹
    private lateinit var mCurrentFile: File


    private val storagePermissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback<ActivityResult?> {
                Log.e(TAG, "RESULT_BACK")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
//                    initView(true)
                        Log.e(TAG, "permission_grant")
                        // Permission granted. Now resume your workflow.
                    } else {
//                    initView(false)
                    }
                }
            })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleFilesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPermissions()

        initView()
    }

    private fun initView() {
        binding.recFiles.adapter = adapter
        adapter.setOnItemClick {

            if (it.path.contains(dataPath)) {
                if (!fileUriUtils.isGrant(this)) {
                    fileUriUtils.startForRoot(this, REQUEST_GRANT_DATA)
                } else {
                    getFileDocumentFile(it)
                }
            } else {
                getFileNormal(it)
            }
        }

        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            mExternalFile = Environment.getExternalStorageDirectory()
            mCurrentFile = mExternalFile
        }

        getFileNormal(mExternalFile)

    }

    private fun getFileDocumentFile(it: File) {
        Log.d(TAG, "file path : ${it.path}")
        val pathList = it.path.split("/")
        val uri = fileUriUtils.changeToUri(it.path)
        Log.d(TAG, "file path2 : ${uri}")

        var templeDocumentFile = DocumentFile.fromTreeUri(
            this, Uri.parse(uri)
        )

        while (templeDocumentFile!!.name != pathList.last()) {
            for (doc in templeDocumentFile!!.listFiles()) {
                for (filename in pathList) {
                    if (doc.name!! == filename) {
                        templeDocumentFile = doc
                    }
                }
            }
        }

        Log.e(
            TAG, "documentFile.uri2 " + templeDocumentFile?.uri
        )

        Log.e(TAG, "documentFile.size" + templeDocumentFile?.listFiles()?.size)

        fetchAndroidDataFiles(templeDocumentFile!!)
    }

    private fun fetchAndroidDataFiles(documentFile: DocumentFile) {

        val flow = flow<MutableList<File>> {

            var fileData: MutableList<File> = mutableListOf()

            for (file in documentFile.listFiles()) {

                fileData.add(File(fileUriUtils.treeToPath(file.uri.toString())))

//                if (file.isDirectory) {
//                    fileData.add(File(fileUriUtils.treeToPath(file.uri.toString())))
//                } else {
//                    //读取数据写成File
//                    if (file.canRead()) {
//                        fileData.add(documentFileToFile(file))
//                    }
//                }
            }
            emit(fileData)
        }.flowOn(Dispatchers.IO)

        CoroutineScope(Dispatchers.Main).launch {
            flow.collect {
                runOnUiThread {
                    initRecycleView(it)
                }
            }
        }

    }

    private fun getFileNormal(file: File) {

        mCurrentFile = file
        Log.d(TAG, mCurrentFile.path)

        val flow = flow<MutableList<File>> {
            var fileData: MutableList<File> = mutableListOf()
            file.listFiles()?.let {
                fileData = it.filter { it.name.startsWith('.').not() }.toMutableList()
            }
            emit(fileData)
        }.flowOn(Dispatchers.IO)

        CoroutineScope(Dispatchers.Main).launch {
            flow.collect {
                runOnUiThread {
                    initRecycleView(it)
                }
            }
        }
    }

    private fun initRecycleView(fileData: MutableList<File>) {
        adapter.setData(fileData)
    }


    override fun onBackPressed() {
        if (!mCurrentFile.path.equals(mExternalFile.path)) {
            mCurrentFile.parentFile?.let { getFileNormal(it) }
        } else {
            super.onBackPressed()
        }
    }


    //返回授权状态
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uri: Uri? = data?.data
        if (data == null) {
            return
        }
        if (requestCode == REQUEST_GRANT_DATA) {
            data.data?.let {
                contentResolver.takePersistableUriPermission(
                    it,
                    data.flags!! and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                )
            }
            var templeDocumentFile = DocumentFile.fromTreeUri(
                this, uri!!
            )
            Log.e(
                TAG, "documentFile.uri2 " + templeDocumentFile?.uri
            )
            Log.e(TAG, "documentFile.size" + templeDocumentFile?.listFiles()?.size)
            fetchAndroidDataFiles(templeDocumentFile!!)

        }
    }


    private fun documentFileToFile(documentFile: DocumentFile): File {

        val saveFile = File(fileUriUtils.treeToPath(documentFile.uri.toString()))

        val fileName = documentFile.name
        val fileUri = documentFile.uri

        Log.d(TAG, "documentFileToFile fileName=$fileName fileUri=$fileUri")

        try {
            //DocumentFile输入流
            val input = contentResolver.openInputStream(fileUri)
            val outputStream: OutputStream = FileOutputStream(saveFile)
            input.use { input ->
                runCatching {
                    outputStream.use { output ->
                        val buffer = ByteArray(1024) // or other buffer size
                        var read: Int
                        if (input != null) {
                            while (input.read(buffer).also { read = it } != -1) {
                                output.write(buffer, 0, read)
                            }
                        }
                        output.flush()
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return saveFile
    }


    @SuppressLint("CheckResult")
    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Toast.makeText(
                    this, getString(R.string.manage_require_permission), Toast.LENGTH_SHORT
                ).show()
                try {
                    val uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                    var intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
                    storagePermissionResultLauncher.launch(intent)
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    storagePermissionResultLauncher.launch(intent)
                }
            }
        } else {
//            RxPermissions(this).requestEachCombined(
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ).subscribe { permission ->
//                if (permission.granted) {
//                    initView(true)
//                    Log.d("TAG", "permission.granted")
//                } else {
//                    initView(false)
//                    Log.d("TAG", "permission.refuse")
//                }
//            }
        }
    }

}
package com.sunexample.demoforandroidxandkotlin.filemanager

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
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


class SimpleFilesActivity : AppCompatActivity() {

    private val TAG = "SimpleFilesActivity"

    lateinit var binding: ActivitySimpleFilesBinding

    private val adapter = StorageAdapter()

    //根目录
    private lateinit var mExternalFile: File

    lateinit var tempFilePath: String

    private val dataPath = "/storage/emulated/0/Android/data"

    val REQUEST_CODE_FOR_DIR = 0

    //当前文件夹
    private lateinit var mCurrentFile: File


    private val storagePermissionResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult?> {
            Log.e("Tag", "RESULT_BACK")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
//                    initView(true)
                    Log.e("TAG", "permission_grant")
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
            if (it.path.equals(dataPath)) {
                if (!fileUriUtils.isGrant(this)) {
                    fileUriUtils.startForRoot(this, REQUEST_CODE_FOR_DIR)
                } else {
//                    fileUriUtils.getDoucmentFile(this,dataPath)
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
                Log.d(TAG, "$it")
                runOnUiThread {
                    initRecycleView(it)
                }
            }
        }
    }

    private fun initRecycleView(fileData: MutableList<File>) {
        Log.d(TAG, "${fileData.size}")
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
        var uri: Uri?
        if (data == null) {
            return
        }
        if (requestCode == REQUEST_CODE_FOR_DIR) {
            data.data?.let {
                contentResolver.takePersistableUriPermission(
                    it,
                    data.flags!! and (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                )
            }
        }
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
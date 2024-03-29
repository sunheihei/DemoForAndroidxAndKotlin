package com.sunexample.demoforandroidxandkotlin.dialog

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityDialogDemoBinding

class DialogDemoActivity : AppCompatActivity() {

    val TAG = "DialogDemoActivity"
    lateinit var binding: ActivityDialogDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDialogDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.normalDialog.setOnClickListener {
            NormalDoalog(this)
        }
        binding.singleDialog.setOnClickListener {
            SingleChoiseDoalog(this)
        }
        binding.multiplyDialog.setOnClickListener {
            MultiplChoiseDoalog(this)
        }

        binding.fragmentDialog.setOnClickListener {
            FragmentDialog(this)
        }
        binding.bottomFragmentDialog.setOnClickListener {
            BottomDialogFraDemo.Instance().show(supportFragmentManager, "dialogdemo")
        }

    }

    /**
     * 普通Dialog
     */
    fun NormalDoalog(context: Context) {
        MaterialAlertDialogBuilder(context).setTitle("Default Dialog")
            .setIcon(R.mipmap.ic_launcher)
            .setMessage("Default Dialog")
            .setPositiveButton("ok") { dialog, which ->
                Log.d(TAG, "ok")
            }.setNegativeButton("cancel") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    fun SingleChoiseDoalog(context: Context) {
        var index: Int = 0//默认选中位置index
        var type: Int = 0
        MaterialAlertDialogBuilder(context)
            .setTitle("Choise Sizes")
            .setSingleChoiceItems(
                R.array.pop_size,
                index
            ) { dialog, which ->
                type = when (which) {
                    0 -> 0
                    1 -> 1
                    2 -> 2
                    3 -> 3
                    4 -> 4
                    else -> 0
                }
            }
            .setPositiveButton(
                "ok"
            ) { dialog, which -> Log.d(TAG, "type: ${type}") }
            .setNegativeButton(
                "cancel"
            ) { dialog, which -> dialog!!.dismiss() }
            .show();
    }

    fun MultiplChoiseDoalog(context: Context) {

        var checkedItem: BooleanArray = booleanArrayOf(false, false, false, false, false)

        MaterialAlertDialogBuilder(context)
            .setTitle("Choise Sizes")
            .setMultiChoiceItems(R.array.pop_size, checkedItem) { dialog, which, isChecked ->
                Log.d(TAG, "which: ${which} ${isChecked}")
                checkedItem.set(which, isChecked)
                Log.d(TAG, "checkedItem[which]: ${checkedItem[which]}")
            }
            .setPositiveButton(
                "ok"
            ) { dialog, which ->

            }
            .setNegativeButton(
                "cancel"
            ) { dialog, which -> dialog!!.dismiss() }
            .show();
    }


    /**
     * FragmentDialog
     */
    fun FragmentDialog(context: Context) {
        DialogFraDemo.Instance().show(supportFragmentManager, "dialogdemo")
    }

}
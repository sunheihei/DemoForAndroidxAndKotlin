package com.sunexample.demoforandroidxandkotlin.flow

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityFlowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FlowActivity : AppCompatActivity() {

    lateinit var binding: ActivityFlowBinding

    private val TAG = "FlowActivity"

    private val ImageBase64 =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFlowBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val bitmap = getBitmapFromEncodedString(ImageBase64)
        Log.d(TAG, "bitmap: $bitmap")
        binding.imgBase.setImageBitmap(bitmap)


        val flow = flow {
            for (index in 0 until 10) {
                Thread.sleep(1000)
                emit(index)
            }
//            emit(getNum())
        }.flowOn(Dispatchers.IO)


        CoroutineScope(Dispatchers.Main).launch {
            flow.collect {
//                Log.d(TAG, "$it")
                runOnUiThread {
                    binding.tvNum.text = "$it"
                }
            }
        }

    }

    private fun getBitmapFromEncodedString(encodedImage: String?): Bitmap? {
        return if (encodedImage != null) {
            val bytes = Base64.decode(encodedImage, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } else {
            null
        }
    }


    private fun getNum(): Int {
        return 5
    }

}
package com.sunexample.demoforandroidxandkotlin.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityFlowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowActivity : AppCompatActivity() {

    lateinit var binding: ActivityFlowBinding

    private val TAG = "FlowActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFlowBinding.inflate(layoutInflater)

        setContentView(binding.root)


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


    private fun getNum(): Int {
        return 5
    }

}
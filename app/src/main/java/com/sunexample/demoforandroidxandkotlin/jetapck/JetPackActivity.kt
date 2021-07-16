package com.sunexample.demoforandroidxandkotlin.jetapck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.databinding.ActivityJetPackBinding
import com.sunexample.demoforandroidxandkotlin.jetapck.Adapter.SongSheetAdapter
import com.sunexample.demoforandroidxandkotlin.jetapck.bean.MusicBean
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Resource
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class JetPackActivity : AppCompatActivity() {


    lateinit var binding: ActivityJetPackBinding

    val TAG = "JetPackActivity"

//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: SongSheetAdapter

    //    val mSearchViewModule: SearchViewModel by viewModels {
//        viewModelFactory
//    }
    private val mSearchViewModule: SearchViewModel by viewModels()


    private lateinit var mSearchKey: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJetPackBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.recSearchResult.adapter = adapter

        binding.btnSearch.setOnClickListener {
            mSearchKey = binding.edSearch.text.toString()

            mSearchKey.let {
                Log.d(TAG,"mSearchKey")
                mSearchViewModule.searchVideo(it)
            }
        }

        mSearchViewModule.repoResult.observe(this, object : Observer<Resource<List<MusicBean>>> {
            override fun onChanged(t: Resource<List<MusicBean>>?) {
                t?.let {
                    when {
                        t.status == Status.SUCCESS -> {
                            Log.d(TAG, "Status ： ${t.status}   Data : ${t.data!!.size}")
                            binding.progressbar?.let {
                                it.visibility = View.GONE
                            }
                            adapter.submitList(t.data)
                        }
                        t.status == Status.LOADING -> {
                            Log.d(TAG, "Status ： ${t.status}   Data : ${t.data}")
                        }
                        t.status == Status.ERROR -> {
                            Log.d(TAG, "Status ： ${t.status}   Data : ${t.data}")
                        }
                        else -> null
                    }
                }

            }
        })
    }

}
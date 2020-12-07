package com.sunexample.demoforandroidxandkotlin.jetapck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.jetapck.Adapter.SongSheetAdapter
import com.sunexample.demoforandroidxandkotlin.jetapck.bean.MusicBean
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Resource
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_jet_pack.*
import javax.inject.Inject

@AndroidEntryPoint
class JetPackActivity : AppCompatActivity() {


    val TAG = "JetPackActivity"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: SongSheetAdapter

    val mSearchViewModule: SearchViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var mSearchKey: String

//    val adapter = SongSheetAdapter(supportFragmentManager)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jet_pack)


        search_rec.adapter = adapter

        btn_search.setOnClickListener {
            mSearchKey = ed_search.text.toString()

            mSearchKey.let {
                mSearchViewModule.searchVideo(it)
            }
        }

        mSearchViewModule.repoResult.observe(this, object : Observer<Resource<List<MusicBean>>> {
            override fun onChanged(t: Resource<List<MusicBean>>?) {
                t?.let {
                    when {
                        t.status == Status.SUCCESS -> {
                            Log.d(TAG, "Status ： ${t.status}   Data : ${t.data!!.size}")
                            progressbar?.let {
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
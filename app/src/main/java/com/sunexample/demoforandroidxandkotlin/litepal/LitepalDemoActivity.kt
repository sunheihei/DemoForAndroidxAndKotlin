package com.sunexample.demoforandroidxandkotlin.litepal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.litepal.bean.News
import kotlinx.android.synthetic.main.activity_litepal_demo.*
import org.litepal.LitePal
import java.util.*


class LitepalDemoActivity : AppCompatActivity() {

    lateinit var adapter: litepalRecAdapter
    private var NewsData: MutableList<News> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_litepal_demo)
        init()
    }

    private fun init() {
        litepal_add.setOnClickListener {
            LitePalAdd()
        }

        litepal_delete.setOnClickListener {
            LitePalDelete()
        }

        litepal_updata.setOnClickListener {
            LitePalUpData()
        }

        litepal_find.setOnClickListener {
            LitePalFind()
        }

        initRec()
    }

    private fun initRec() {
        NewsData = LitePal.findAll(News::class.java)
        adapter = litepalRecAdapter(this)
        adapter.setData(NewsData)
        rec_sql.adapter = adapter

    }

    private fun refrushData() {
        NewsData = LitePal.findAll(News::class.java)
        Log.d("LITEPAL", NewsData.size.toString())
        adapter.setData(NewsData)
    }


    private fun LitePalAdd() {
        val new = News()
        new.content = "content1"
        new.title = "title1"
        new.commentCount = 2
        new.save()


        refrushData()
    }

    private fun LitePalDelete() {
        LitePal.delete(News::class.java, 1)
    }

    private fun LitePalUpData() {

    }

    private fun LitePalFind() {
        val lastNews: News = LitePal.findLast(News::class.java)

        val newsList: List<News> = LitePal.findAll(News::class.java, 1, 3, 5, 7)
    }


}
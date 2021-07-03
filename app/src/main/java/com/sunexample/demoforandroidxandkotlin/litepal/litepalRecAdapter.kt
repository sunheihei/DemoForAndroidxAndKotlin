package com.sunexample.demoforandroidxandkotlin.litepal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.litepal.bean.News

class litepalRecAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<News>? = null

    fun setData(data: List<News>?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_litepal_demo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsHolder) {
            holder.tv_litepal_news!!.setText(
                "${data!!.get(position).id}    ${data!!.get(position).title}  ${
                    data!!.get(
                        position
                    ).content
                }"
            )
        }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }


    class NewsHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var tv_litepal_news: TextView? = null

        init {
            tv_litepal_news = itemview.findViewById<TextView>(R.id.tv_news)
        }
    }

}
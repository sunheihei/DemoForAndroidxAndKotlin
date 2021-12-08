package com.sunexample.demoforandroidxandkotlin.RecycleView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.RecycleView.bean.AdBean
import com.sunexample.demoforandroidxandkotlin.RecycleView.bean.Content

class ContentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var Data = mutableListOf<Any>()

    val TYPE_CONTENT = 0
    val TYPE_AD = 1

    fun setData(mContext: Context, data: MutableList<Any>) {
        this.Data = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (Data[position] is Content) {
            return TYPE_CONTENT
        } else if (Data[position] is AdBean) {
            return TYPE_AD
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View

        if (viewType == TYPE_CONTENT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rec_view_layout, parent, false)
            return ContentHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rec_view_layout, parent, false)
            return ADHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentHolder) {
            holder.bind(Data[position] as Content)
        } else if (holder is ADHolder) {
            holder.bind(Data[position] as AdBean)
        }
    }

    override fun getItemCount(): Int = Data.size


    class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView

        init {
            tv = itemView.findViewById(R.id.tv_content)
        }

        fun bind(data: Content) {
            tv.setText(data.content)
        }


    }

    class ADHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv: TextView

        init {
            tv = itemView.findViewById(R.id.tv_content)
        }

        fun bind(data: AdBean) {
            tv.setText(data.ad)
        }
    }

}
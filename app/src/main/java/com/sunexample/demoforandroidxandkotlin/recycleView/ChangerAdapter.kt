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
import com.sunexample.demoforandroidxandkotlin.SuperSp

class ChangerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var Data = mutableListOf<Any>()
    var listType = SuperSp.TYPE_GRID_TWO_ROW

    fun setData(mContext: Context, data: MutableList<Any>) {
        this.Data = data
        notifyDataSetChanged()
    }

    fun setViewType() {
        listType =
            if (listType == SuperSp.TYPE_LIST_ONE_ROW) SuperSp.TYPE_GRID_TWO_ROW else SuperSp.TYPE_LIST_ONE_ROW
        SuperSp.spListType = listType
        notifyDataSetChanged()
    }

    fun getViewType() = listType
    override fun getItemViewType(position: Int): Int {
        return if (listType == SuperSp.TYPE_LIST_ONE_ROW) {
            SuperSp.TYPE_LIST_ONE_ROW
        } else {
            SuperSp.TYPE_GRID_TWO_ROW
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lin_item_rec_view_layout, parent, false)
        return ContentHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentHolder) {
            holder.bind(Data[position] as Content)
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

}
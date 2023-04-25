package com.sunexample.demoforandroidxandkotlin.filemanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.databinding.ItemStorageFileLayoutBinding
import java.io.File

class StorageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemClick: ((f: File) -> Unit)? = null
    var mData: MutableList<File> = mutableListOf()


    fun setData(fileData: MutableList<File>) {
        this.mData = fileData
        //每次重新设置数据,重置选择相关的参数
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StorageViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_storage_file_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val resultItem = mData.get(position)
        resultItem.let {
            if (holder is StorageViewHolder) {
                holder.bind(resultItem)
            }
        }
    }


    inner class StorageViewHolder(private val binding: ItemStorageFileLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(f: File) {
            binding.tvFileName.text = f.name
            binding.root.setOnClickListener {
                itemClick?.invoke(f)
            }
            
        }
    }


    fun setOnItemClick(itemClick: (f: File) -> Unit) {
        this.itemClick = itemClick
    }

}
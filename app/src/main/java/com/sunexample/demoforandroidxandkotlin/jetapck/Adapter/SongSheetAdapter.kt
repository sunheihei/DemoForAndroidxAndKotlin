package com.sunexample.demoforandroidxandkotlin.jetapck.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sunexample.demoforandroidxandkotlin.R
import com.sunexample.demoforandroidxandkotlin.jetapck.bean.MusicBean
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject


/**
 * @author Amer
 * @datetime: 2020/5/11 15:42
 * @description:最高使用率歌单适配器
 */
class SongSheetAdapter @Inject constructor(@ActivityContext context: Context) :
    ListAdapter<MusicBean, SongSheetViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongSheetViewHolder {
        parent.context
        return SongSheetViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: SongSheetViewHolder, position: Int) {

        val musicBean = getItem(position)
        if (musicBean != null) {
            holder.bind(musicBean)
        }
    }


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MusicBean>() {
            override fun areItemsTheSame(oldItem: MusicBean, newItem: MusicBean): Boolean =
                oldItem.mVideoId == newItem.mVideoId

            override fun areContentsTheSame(oldItem: MusicBean, newItem: MusicBean): Boolean =
                oldItem == newItem
        }
    }

}


class SongSheetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var musicbean: MusicBean
    private lateinit var repo_name: TextView
    private lateinit var repo_img: ImageView

    init {
        repo_name = itemView.findViewById(R.id.repo_name)
        repo_img = itemView.findViewById(R.id.repo_img)
    }

    fun bind(musicBean: MusicBean) {
        if (musicBean == null) return
        showRepoData(musicBean)
    }

    private fun showRepoData(musicBean: MusicBean) {
        this.musicbean = musicBean
        //绑定数据
        repo_name.setText(musicBean.mTitle)
        Glide.with(itemView.context).load(musicbean.mThumbnails).into(repo_img)
    }

    companion object {
        fun create(parent: ViewGroup) = SongSheetViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.saerch_repo_layout, parent, false)
        )
    }

}

package com.sunexample.demoforandroidxandkotlin.jetapck

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sunexample.demoforandroidxandkotlin.User
import com.sunexample.demoforandroidxandkotlin.jetapck.api.SearchService
import com.sunexample.demoforandroidxandkotlin.jetapck.bean.MusicBean
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Resource
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class SearchRepository(private val service: SearchService, private val nextservice: SearchService) {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val TAG = "SearchRepository"

    var VideoData: MutableLiveData<Resource<List<MusicBean>>> = MutableLiveData()

    private var isRequestInProgress = false


    private val key = "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8"
    private var mNextToken = ""

    fun searchvideo(query: String): MutableLiveData<Resource<List<MusicBean>>> {
        requestVideoData(query)
        return VideoData
    }

    private fun requestVideoData(query: String) {

        var mQueryKey = query.toLowerCase()

        mQueryKey = mQueryKey.replace(" ", "+")


//        Log.d(TAG, "mQueryKey：$mQueryKey")

        if (isRequestInProgress) return
        VideoData.postValue(Resource(Status.LOADING, null, "LOADING"))
        isRequestInProgress = true
        uiScope.launch {
            val response =
                service.getSearchVideo(mQueryKey, "1")
            if (response.isSuccessful) {
                response.body()?.let {
                    val allResults = mutableListOf<MusicBean>()
                    allResults.addAll(parseVideo(it, true))
                    VideoData.postValue(Resource(Status.SUCCESS, allResults, "success"))
                }
                isRequestInProgress = false
            } else {
                VideoData.postValue(Resource(Status.ERROR, null, "ERROR"))
                isRequestInProgress = false
            }
        }
    }


    private suspend fun parseVideo(data: String, isFirst: Boolean) = withContext(Dispatchers.IO) {
        return@withContext ParseVideoData(data, isFirst)
    }

    //解析Video搜索结果
    fun ParseVideoData(data: String, isFirst: Boolean): List<MusicBean> {
        val mDataList: MutableList<MusicBean> = ArrayList()
        var obj = JSONArray()
        var Temobj: JSONArray
        var Tokenobj: JSONObject
        if (isFirst) {
            try {
                Temobj =
                    JSONArray(data).getJSONObject(1).getJSONObject("response")
                        .getJSONObject("contents")
                        .getJSONObject("twoColumnSearchResultsRenderer")
                        .getJSONObject("primaryContents")
                        .getJSONObject("sectionListRenderer").getJSONArray("contents")
                obj = Temobj.getJSONObject(0).getJSONObject("itemSectionRenderer")
                    .getJSONArray("contents")
                Tokenobj = Temobj.getJSONObject(1).getJSONObject("continuationItemRenderer")
                    .getJSONObject("continuationEndpoint").getJSONObject("continuationCommand")
                mNextToken = Tokenobj.getString("token")
            } catch (e: Exception) {
            }
        } else {
            try {
                Temobj =
                    JSONObject(data).getJSONArray("onResponseReceivedCommands").getJSONObject(0)
                        .getJSONObject("appendContinuationItemsAction")
                        .getJSONArray("continuationItems")
                obj = Temobj.getJSONObject(0).getJSONObject("itemSectionRenderer")
                    .getJSONArray("contents")
                Tokenobj = Temobj.getJSONObject(1).getJSONObject("continuationItemRenderer")
                    .getJSONObject("continuationEndpoint").getJSONObject("continuationCommand")
                mNextToken = Tokenobj.getString("token")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        for (index in 0 until obj.length()) {
            val videoRenderer = try {
                obj.getJSONObject(index).getJSONObject("videoRenderer")
            } catch (e: Exception) {
                continue
            }

            val videoId = try {
                videoRenderer.getString("videoId")
            } catch (e: Exception) {
                continue
            }

            val title = try {
                videoRenderer.getJSONObject("title").getJSONArray("runs").getJSONObject(0)
                    .getString("text")
            } catch (e: Exception) {
                continue
            }


            val thumbnail = try {
                val tempthumbnail =
                    videoRenderer.getJSONObject("thumbnail").getJSONArray("thumbnails")
                tempthumbnail.getJSONObject(tempthumbnail.length() - 1).getString("url")
            } catch (e: Exception) {
                continue
            }


            val time = try {
                videoRenderer.getJSONObject("lengthText").getString("simpleText")
            } catch (e: Exception) {
                continue
            }

            val viewCount = try {
                videoRenderer.getJSONObject("viewCountText").getString("simpleText")
            } catch (e: Exception) {
                continue
            }


            val subtitle: StringBuilder
            try {
                subtitle = StringBuilder().append("")
                val temp =
                    videoRenderer.getJSONObject("descriptionSnippet").getJSONArray("runs")
                for (index in 0 until temp.length()) {
                    subtitle.append(temp.getJSONObject(index).getString("text"))
                }
            } catch (e: Exception) {
                continue
            }

//            Log.d(TAG, "videoId: ${videoId}")
//            Log.d(TAG, "title: ${title}")
//            Log.d(TAG, "thumbnail: ${thumbnail}")
//            Log.d(TAG, "subtitle: ${subtitle}")
//            Log.d(TAG, "time: ${time}")
//            Log.d(TAG, "viewCount: ${viewCount}")


            val video = MusicBean(
                mVideoId = videoId,
                mTitle = title,
                mThumbnails = thumbnail,
                mChannelTitle = subtitle.toString(),
                mDuration = time,
                mViewCount = viewCount
            )
            mDataList.add(video)

        }
        return mDataList
    }

}
package com.sunexample.demoforandroidxandkotlin.jetapck

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sunexample.demoforandroidxandkotlin.jetapck.api.SearchService
import com.sunexample.demoforandroidxandkotlin.jetapck.bean.MusicBean
import com.sunexample.demoforandroidxandkotlin.jetapck.common.ConstantStringForSearchBody
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Resource
import com.sunexample.demoforandroidxandkotlin.jetapck.common.Status
import com.sunexample.demoforandroidxandkotlin.jetapck.di.BindOneService
import com.sunexample.demoforandroidxandkotlin.jetapck.di.BindTwoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import javax.inject.Inject

class SearchRepository @Inject constructor(
    @BindOneService private val service: SearchService,
    @BindTwoService private val servicenext: SearchService
) {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val TAG = "SearchRepository"
    var VideoDataList = mutableListOf<MusicBean>()  //VideoData

    var VideoLiveData: MutableLiveData<Resource<List<MusicBean>>> = MutableLiveData()

    private var isRequestInProgress = false


    private val key = "AIzaSyAO_FJ2SlqU8Q4STEHLGCilw_Y9_11qcW8"
    private var mNextToken: String = "a"

    fun searchvideo(query: String): MutableLiveData<Resource<List<MusicBean>>> {
        if (mNextToken.equals("a")) {
            requestVideoData(query)
            Log.d(TAG, "requestVideoData")
        } else {
            Log.d(TAG, "requestMoreVideoData")
            requestMoreVideoData(query)
        }
        return VideoLiveData
    }

    private fun requestVideoData(query: String) {
        var mQueryKey = query.toLowerCase()
        mQueryKey = mQueryKey.replace(" ", "+")
        if (isRequestInProgress) return
        VideoLiveData.postValue(Resource(Status.LOADING, null, "LOADING"))
        isRequestInProgress = true
        uiScope.launch {
            try {
                val response =
                    service.getSearchVideo(mQueryKey, "1")
                if (response.isSuccessful) {
                    response.body()?.let {
                        VideoDataList.addAll(ParseVideoData(it, true))
                        VideoLiveData.postValue(Resource(Status.SUCCESS, VideoDataList, "SUCCESS"))
                    }
                    isRequestInProgress = false
                } else {
                    VideoLiveData.postValue(Resource(Status.ERROR, null, "ERROR"))
                    isRequestInProgress = false
                }
            } catch (e: Exception) {
                VideoLiveData.postValue(Resource(Status.ERROR, null, "ERROR"))
            }
        }
    }

    //请求Video下一页
    private fun requestMoreVideoData(query: String) {
        var mQueryKey = query.toLowerCase()
        mQueryKey = mQueryKey.replace(" ", "+")
        if (isRequestInProgress) return
        VideoLiveData.postValue(Resource(Status.LOADING, null, "LOADING"))
        isRequestInProgress = true
        uiScope.launch {
            try {
                val RequestBody = ConstantStringForSearchBody.getVideoSearchBody(mNextToken)
                    .toRequestBody("application/json".toMediaType())
                val response =
                    servicenext.getSearchNextPager(key, RequestBody)
                if (response.isSuccessful) {
                    response.body()?.let {
                        VideoDataList.addAll(ParseVideoData(it, false))
                        VideoLiveData.postValue(Resource(Status.SUCCESS, VideoDataList, "SUCCESS"))
                    }
                    isRequestInProgress = false
                } else {
                    VideoLiveData.postValue(Resource(Status.ERROR, null, "ERROR"))
                    isRequestInProgress = false
                }
            } catch (e: Exception) {
                VideoLiveData.postValue(Resource(Status.ERROR, null, "ERROR"))
            }
        }
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
                ""
            }


            val thumbnail = try {
                val tempthumbnail =
                    videoRenderer.getJSONObject("thumbnail").getJSONArray("thumbnails")
                tempthumbnail.getJSONObject(tempthumbnail.length() - 1).getString("url")
            } catch (e: Exception) {
                ""
            }


            val time = try {
                videoRenderer.getJSONObject("lengthText").getString("simpleText")
            } catch (e: Exception) {
                continue
            }

            val viewCount = try {
                videoRenderer.getJSONObject("viewCountText").getString("simpleText")
            } catch (e: Exception) {
                ""
            }


            val subtitle = try {
//                subtitle = StringBuilder().append("")
//                val temp =
//                        videoRenderer.getJSONObject("descriptionSnippet").getJSONArray("runs")
//                for (index in 0 until temp.length()) {
//                    subtitle.append(temp.getJSONObject(index).getString("text"))
//                }
                videoRenderer.getJSONArray("detailedMetadataSnippets").getJSONObject(0)
                    .getJSONObject("snippetText").getJSONArray("runs").getJSONObject(0)
                    .getString("text")
            } catch (e: Exception) {
                ""
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
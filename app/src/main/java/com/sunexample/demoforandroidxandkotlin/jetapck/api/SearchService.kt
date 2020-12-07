package com.sunexample.demoforandroidxandkotlin.jetapck.api

import com.sunexample.demoforandroidxandkotlin.jetapck.common.Const
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

import retrofit2.http.*


interface SearchService {

    @GET("results?")
    suspend fun getSearchList(
        @Query("search_query", encoded = true) key: String,
        @Query("sp", encoded = true) sp: String = "EgIQAw%253D%253D", //筛选条件：1.Playlist 2.Relevance
        @Query("pbj") pbj: String = Const.YouTubePBJ, //请求json
        @Header("Connection") connect: String = Const.YouTubeConnection,
        @Header("User-Agent") user_Agent: String = Const.YouTubeUserAgent,
        @Header("X-YouTube-Client-Name") clientName: String = Const.XYouTubeClientName,
        @Header("X-YouTube-Client-Version") clientVersion: String = Const.XYoutubeClientVersion
    ): Response<String>


    @GET("results?")
    suspend fun getSearchVideo(
        @Query("search_query") key: String,
        @Query("pbj") pbj: String = Const.YouTubePBJ,
        @Header("Connection") connect: String = Const.YouTubeConnection,
        @Header("User-Agent") user_Agent: String = Const.YouTubeUserAgent,
        @Header("X-YouTube-Client-Name") youtubeclientname: String = Const.XYouTubeClientName,
        @Header("X-YouTube-Client-Version") youtubeclientversion: String = Const.XYoutubeClientVersion
//        @Query("sp", encoded = true) sp: String
    ): Response<String>

    @Headers(
        "Connection: Keep-Alive",
        "Host:www.youtube.com",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64; rv:68.0) Gecko/20100101 Firefox/68.0",
        "X-YouTube-Client-Name: 1",
        "X-YouTube-Client-Version: 2.20200214.04.00"
    )
    @POST("search?")
    suspend fun getSearchNextPager(
//        @Header("Referer") Referer: String,
        @Query("key") key: String,
        @Body nexttoken: RequestBody
    ): Response<String>

}
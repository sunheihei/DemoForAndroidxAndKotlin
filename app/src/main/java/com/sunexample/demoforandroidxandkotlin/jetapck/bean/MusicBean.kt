package com.sunexample.demoforandroidxandkotlin.jetapck.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicBean(
    var mVideoId: String = "",
    var mTitle: String = "",
    var mThumbnails: String = "",
    var mChannelTitle: String = "",
    var mDuration: String = "",
    var mViewCount: String = "",
    var mPlaylistId: String = "",
    var mDate: String = "",
    var mLastPlayTime: String = "",
    var mCurrentRanks: String = "",
    var mPreviousRanks: String = "",
    var mPeriodsOnChart: String = ""
) : Parcelable
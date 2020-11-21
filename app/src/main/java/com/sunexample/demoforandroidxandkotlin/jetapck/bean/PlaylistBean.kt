package com.sunexample.demoforandroidxandkotlin.jetapck.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PlaylistBean(
    var id: String? = "",
    var title: String = "",
    var description: String? = "",
    var thumbnail: String = "",
    var create_date: String? = "",
    var update_date: String? = "",
    var type: String? = "",
    var type_id: String? = "",
    var type_category: String? = "",
    var count: String? = "",
    var views: String? = "",
    var playlistId: String = ""
) : Parcelable

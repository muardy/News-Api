package com.ardy.jobsubmis.connect

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class NewsDataApi (
    var namenews: String = "",
    var date: String = "",
    var desk: String = "",
    var url: String = "",
    var photo:  String = ""
): Parcelable
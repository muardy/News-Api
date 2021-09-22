package com.ardy.jobsubmis.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsData (
    var name: String = "",
    var desk: String = "",
    var photo: Int = 0

): Parcelable
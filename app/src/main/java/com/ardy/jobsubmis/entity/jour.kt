package com.ardy.jobsubmis.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class jour(
    var id: Int = 0,
    var title: String? = null,
    var birth: String? = null,
    var date: String? = null
): Parcelable
package com.ardy.jobsubmis.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "jour"
            const val _ID = "_id"
            const val TITLE = "title"
            const val BIRTH = "birth"
            const val DATE = "date"
        }
    }

}
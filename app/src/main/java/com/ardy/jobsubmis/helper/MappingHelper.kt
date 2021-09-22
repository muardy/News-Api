package com.ardy.jobsubmis.helper

import android.database.Cursor
import com.ardy.jobsubmis.db.DatabaseContract
import com.ardy.jobsubmis.entity.jour

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<jour> {
        val notesList = ArrayList<jour>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
                val birth = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.BIRTH))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
                notesList.add(jour(id, title, birth, date))
            }
        }
        return notesList
    }

}
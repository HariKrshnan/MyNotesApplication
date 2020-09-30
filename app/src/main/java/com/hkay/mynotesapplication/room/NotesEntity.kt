package com.hkay.mynotesapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class  NotesEntity (@PrimaryKey @ColumnInfo(name = "textContent") var textContent: String)
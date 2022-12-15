package com.example.nycschooldb.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "school_list")
data class NycSchoolEntity(
    @PrimaryKey
    val dbn: String,
    @ColumnInfo(name = "name")
    val school_name: String,
    val address: String,
    val city: String,
    val zip: String
)

data class NycSchoolSatEntity(
    val dbn: String,
    val schoolName: String,
    val satTestTaker: String,
    val readingAvg: String,
    val mathAvg: String,
    val writingAvg: String
)

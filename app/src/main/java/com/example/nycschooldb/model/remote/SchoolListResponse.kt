package com.example.nycschooldb.model.remote

import com.google.gson.annotations.SerializedName

data class SchoolListResponse(
    @SerializedName("dbn")
    val dbn: String,
    @SerializedName("school_name")
    val school_name: String,
    @SerializedName("primary_address_line_1")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("zip")
    val zip: String
)

data class SchoolSatResponse(
    val dbn: String,
    @SerializedName("school_name")
    val school_name: String,
    @SerializedName("num_of_sat_test_takers")
    val satTestTakers: String,
    @SerializedName("sat_critical_reading_avg_score")
    val readingAvg: String,
    @SerializedName("sat_math_avg_score")
    val mathAvg: String,
    @SerializedName("sat_writing_avg_score")
    val writingAvg: String
)

package com.example.nycschooldb.model.remote

import com.example.nycschooldb.common.Utils.Companion.END_POINT_SAT
import com.example.nycschooldb.common.Utils.Companion.END_POINT_SCHOOLS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NycApiService {
    @GET(END_POINT_SCHOOLS)
    suspend fun getSchoolList(): Response<List<SchoolListResponse>>

    @GET(END_POINT_SAT)
    suspend fun getSchoolSat(
        @Query("dbn") dbn: String): Response<List<SchoolSatResponse>>
}
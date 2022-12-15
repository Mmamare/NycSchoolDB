package com.example.nycschooldb.model.remote

sealed class UIState{
    data class ResponseListSchool(val schoolData: List<SchoolListResponse>):UIState()
    data class ResponseeSchoolSat(val satData: List<SchoolSatResponse>):UIState()
    data class Error(val errorMessage: String): UIState()
    data class Loading(val isLOading: Boolean): UIState()
}

package com.example.nycschooldb.repository

import com.example.nycschooldb.common.NetworkManager
import com.example.nycschooldb.model.local.NycDao
import com.example.nycschooldb.model.local.NycSchoolEntity
import com.example.nycschooldb.model.local.NycSchoolSatEntity
import com.example.nycschooldb.model.remote.NycApiService
import com.example.nycschooldb.model.remote.SchoolListResponse
import com.example.nycschooldb.model.remote.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

interface NycRepository {
    suspend fun useCaseSchoolList(): Flow<UIState>
    suspend fun useCaseSatINfo(dbn: String): Flow<UIState>
}


class NycRepoImpl @Inject constructor(
    private val networkManager: NetworkManager,
    private val nycApiService: NycApiService,
    private val nycDao: NycDao): NycRepository{

    override suspend fun useCaseSchoolList(): Flow<UIState> {
        return flow {
            emit(UIState.Loading(true))
            delay(500)

            if (networkManager.isConnected){
                val response = nycApiService.getSchoolList()
                if (response.isSuccessful)
                    response.body()?.let {remote->
                        remote.map {
                           NycSchoolEntity(
                               dbn = it.dbn,
                               school_name = it.school_name,
                               address = it.address,
                               city = it.city,
                               zip = it.zip
                           ) }.forEach { school->
                            nycDao.update(school)
                        }
                    }
                emit(
                    UIState.ResponseListSchool(
                        nycDao.getListSchools()
                            .map {
                                SchoolListResponse(
                                    dbn = it.dbn,
                                    school_name = it.school_name,
                                    address = it.address,
                                    city = it.city,
                                    zip = it.zip
                                )
                            }
                    )
                )
            }else{
                emit(UIState.ResponseListSchool(
                    nycDao.getListSchools().map {
                        SchoolListResponse(
                            dbn = it.dbn,
                            school_name = it.school_name,
                            address = it.address,
                            city = it.city,
                            zip = it.zip
                        )
                    }
                ))
            }
        }
    }

    override suspend fun useCaseSatINfo(dbn: String): Flow<UIState> {
        return flow {
            emit(UIState.Loading(true))
            delay(500)

            if (networkManager.isConnected){
                val response = nycApiService.getSchoolSat(dbn)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(UIState.ResponseeSchoolSat(it))
                    } ?: UIState.Error("Empty Score")
                }else{
                    emit(UIState.Error("Failure SAT response"))
                }

            }else{
                emit(UIState.Error("NO INTERNET CONNECTION"))
            }
        }
    }

}
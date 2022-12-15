package com.example.nycschooldb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nycschooldb.model.remote.UIState
import com.example.nycschooldb.repository.NycRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NycSchoolViewModel @Inject constructor(
    private val repository: NycRepository,
    private val dispatcher: CoroutineDispatcher): ViewModel(){

    private val _schoolInfo = MutableLiveData<UIState>()
    val schoolInfo: LiveData<UIState>
    get() = _schoolInfo

    private val _satScore = MutableLiveData<UIState>()
    val satScore: LiveData<UIState>
        get() = _satScore

    init {
        getSchoolInfo()
    }

    private fun getSchoolInfo() {
        CoroutineScope(dispatcher).launch {
            repository.useCaseSchoolList()
                .catch {
                    _schoolInfo.postValue(UIState.Error("No school to show"))
                }
                .collect{
                    _schoolInfo.postValue(it)
                }
        }
    }

    fun getSatScore(dbn: String){
        CoroutineScope(dispatcher).launch {
            repository.useCaseSatINfo(dbn)
                .catch {
                    _satScore.postValue(UIState.Error("No scores to show"))
                }
                .collect{
                    _satScore.postValue(it)
                }
        }
    }

    fun setScoreLoading() {
        _satScore.value = UIState.Loading(true)
    }

}
package com.anangkur.mediku.feature.view.mens.menstrualEdit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.feature.mapper.MenstrualPeriodResumeMapper
import com.anangkur.mediku.feature.model.menstrual.MenstrualPeriodResumeIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MenstrualEditViewModel(private val repository: Repository): ViewModel() {

    private val menstrualPeriodResumeMapper = MenstrualPeriodResumeMapper.getInstance()

    var selectedCalendar: Calendar? = null

    var menstrualIntentLive = MutableLiveData<MenstrualPeriodResumeIntent>()
    var isEdit = false

    val progressAddMenstrualRecord = MutableLiveData<Boolean>()
    val successAddMenstrualRecord = MutableLiveData<MenstrualPeriodResumeIntent>()
    val errorAddMenstrualRecord = MutableLiveData<String>()
    fun addMenstrualPeriod(menstrualPeriodResume: MenstrualPeriodResumeIntent){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addMenstrualPeriod(menstrualPeriodResumeMapper.mapFromIntent(menstrualPeriodResume)!!, object: BaseFirebaseListener<MenstrualPeriodResume> {
                override fun onLoading(isLoading: Boolean) {
                    progressAddMenstrualRecord.postValue(isLoading)
                }
                override fun onSuccess(data: MenstrualPeriodResume) {
                    successAddMenstrualRecord.postValue(menstrualPeriodResumeMapper.mapToIntent(data))
                }
                override fun onFailed(errorMessage: String) {
                    errorAddMenstrualRecord.postValue(errorMessage)
                }
            })
        }
    }
}
package com.anangkur.mediku.feature.view.mens.menstrualEdit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MenstrualEditViewModel(private val repository: Repository): ViewModel() {

    var selectedCalendar: Calendar? = null

    var menstrualIntentLive = MutableLiveData<MenstrualPeriodResume>()
    var isEdit = false

    val progressAddMenstrualRecord = MutableLiveData<Boolean>()
    val successAddMenstrualRecord = MutableLiveData<MenstrualPeriodResume>()
    val errorAddMenstrualRecord = MutableLiveData<String>()
    fun addMenstrualPeriod(menstrualPeriodResume: MenstrualPeriodResume){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addMenstrualPeriod(menstrualPeriodResume, object: BaseFirebaseListener<MenstrualPeriodResume> {
                override fun onLoading(isLoading: Boolean) {
                    progressAddMenstrualRecord.postValue(isLoading)
                }
                override fun onSuccess(data: MenstrualPeriodResume) {
                    successAddMenstrualRecord.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorAddMenstrualRecord.postValue(errorMessage)
                }
            })
        }
    }
}
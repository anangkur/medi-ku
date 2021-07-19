package com.anangkur.mediku.feature.view.dashboard.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.feature.mapper.MedicalRecordMapper
import com.anangkur.mediku.feature.mapper.UserMapper
import com.anangkur.mediku.feature.model.auth.UserIntent
import com.anangkur.mediku.feature.model.medical.MedicalRecordIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {

    private val userMapper = UserMapper.getInstance()
    private val medicalRecordMapper = MedicalRecordMapper.getInstance()

    private val triggerGetNews = MutableLiveData<String>()
    val healthNewsLive = Transformations.switchMap(triggerGetNews){
        repository.getHealthNewsByCountry(it)
    }
    fun getNews(){
        triggerGetNews.postValue(repository.loadCountry())
    }

    val progressGetMedicalRecord = MutableLiveData<Boolean>()
    val successGetMedicalRecord = MutableLiveData<List<MedicalRecordIntent>>()
    val errorGetMedicalRecord = MutableLiveData<String>()
    fun getMedicalRecord(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getMedicalRecords(object: BaseFirebaseListener<List<MedicalRecord>>{
                override fun onLoading(isLoading: Boolean) {
                    progressGetMedicalRecord.postValue(isLoading)
                }
                override fun onSuccess(data: List<MedicalRecord>) {
                    successGetMedicalRecord.postValue(data.map { medicalRecordMapper.mapToIntent(it) })
                }
                override fun onFailed(errorMessage: String) {
                    errorGetMedicalRecord.postValue(errorMessage)
                }
            })
        }
    }

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<UserIntent>()
    val errorGetProfile = MutableLiveData<String>()
    fun getUserProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUser(object: BaseFirebaseListener<User?>{
                override fun onLoading(isLoading: Boolean) {
                    progressGetProfile.postValue(isLoading)
                }
                override fun onSuccess(data: User?) {
                    successGetProfile.postValue(data?.let { userMapper.mapToIntent(it) })
                }
                override fun onFailed(errorMessage: String) {
                    errorGetProfile.postValue(errorMessage)
                }
            })
        }
    }
}
package com.anangkur.mediku.feature.dashboard.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.medical.MedicalRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {

    private val triggerGetNews = MutableLiveData<String>()
    val healthNewsLive = Transformations.switchMap(triggerGetNews){
        repository.getHealthNewsByCountry(it)
    }
    fun getNews(){
        triggerGetNews.postValue(repository.loadCountry())
    }

    val progressGetMedicalRecord = MutableLiveData<Boolean>()
    val successGetMedicalRecord = MutableLiveData<List<MedicalRecord>>()
    val errorGetMedicalRecord = MutableLiveData<String>()
    fun getMedicalRecord(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getMedicalRecords(object: BaseFirebaseListener<List<MedicalRecord>>{
                override fun onLoading(isLoading: Boolean) {
                    progressGetMedicalRecord.postValue(isLoading)
                }
                override fun onSuccess(data: List<MedicalRecord>) {
                    successGetMedicalRecord.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorGetMedicalRecord.postValue(errorMessage)
                }
            })
        }
    }

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<User>()
    val errorGetProfile = MutableLiveData<String>()
    fun getUserProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUser(object: BaseFirebaseListener<User?>{
                override fun onLoading(isLoading: Boolean) {
                    progressGetProfile.postValue(isLoading)
                }
                override fun onSuccess(data: User?) {
                    successGetProfile.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorGetProfile.postValue(errorMessage)
                }
            })
        }
    }
}
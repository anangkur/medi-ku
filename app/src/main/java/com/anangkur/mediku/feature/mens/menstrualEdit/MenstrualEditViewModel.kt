package com.anangkur.mediku.feature.mens.menstrualEdit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.util.Const
import com.anangkur.mediku.util.getTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MenstrualEditViewModel(private val repository: Repository): ViewModel() {

    var selectedCalendar: Calendar? = null

    val progressAddMenstrualRecord = MutableLiveData<Boolean>()
    val successAddMenstrualRecord = MutableLiveData<MenstrualPeriodResume>()
    val errorAddMenstrualRecord = MutableLiveData<String>()
    fun addMenstrualPeriod(menstrualPeriodResume: MenstrualPeriodResume){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressAddMenstrualRecord.postValue(true)
                val user = repository.remoteRepository.firebaseAuth.currentUser
                repository.remoteRepository.firestore.collection(Const.COLLECTION_MENSTRUAL_PERIOD)
                    .document(user?.uid?:"")
                    .collection(menstrualPeriodResume.year)
                    .document(menstrualPeriodResume.month)
                    .set(menstrualPeriodResume)
                    .addOnSuccessListener {
                        progressAddMenstrualRecord.postValue(false)
                        successAddMenstrualRecord.postValue(menstrualPeriodResume)
                    }
                    .addOnFailureListener { exception ->
                        progressAddMenstrualRecord.postValue(false)
                        errorAddMenstrualRecord.postValue(exception.message)
                    }
            }catch (e: Exception){
                progressAddMenstrualRecord.postValue(false)
                errorAddMenstrualRecord.postValue(e.message)
            }
        }
    }
}
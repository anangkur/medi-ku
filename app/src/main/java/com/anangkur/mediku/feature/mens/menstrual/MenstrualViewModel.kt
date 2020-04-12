package com.anangkur.mediku.feature.mens.menstrual

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodMonthly
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.util.Const
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MenstrualViewModel(private val repository: Repository): ViewModel() {

    val progressGetMenstrualRecord = MutableLiveData<Boolean>()
    val successGetMenstrualRecord = MutableLiveData<MenstrualPeriodMonthly>()
    val errorGetMenstrualRecord = MutableLiveData<String>()
    fun getMedicalRecord(date: Date){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressGetMenstrualRecord.postValue(true)
                val user = repository.remoteRepository.firebaseAuth.currentUser
                repository.remoteRepository.firestore
                    .collection(Const.COLLECTION_MENSTRUAL_PERIOD)
                    .document(user?.uid?:"")
                    .collection(SimpleDateFormat("yyyy").format(date))
                    .get()
                    .addOnSuccessListener {result ->
                        val menstrualPeriodMonthly = MenstrualPeriodMonthly()
                        if (!result.isEmpty){
                            for (querySnapshot in result){

                                val data = querySnapshot?.toObject<MenstrualPeriodResume>()

                                when (querySnapshot.id){
                                    Const.KEY_JAN -> { menstrualPeriodMonthly.jan = data }
                                    Const.KEY_FEB -> { menstrualPeriodMonthly.feb = data }
                                    Const.KEY_MAR -> { menstrualPeriodMonthly.mar = data }
                                    Const.KEY_APR -> { menstrualPeriodMonthly.apr = data }
                                    Const.KEY_MAY -> { menstrualPeriodMonthly.may = data }
                                    Const.KEY_JUN -> { menstrualPeriodMonthly.jun = data }
                                    Const.KEY_JUL -> { menstrualPeriodMonthly.jul = data }
                                    Const.KEY_AUG -> { menstrualPeriodMonthly.aug = data }
                                    Const.KEY_SEP -> { menstrualPeriodMonthly.sep = data }
                                    Const.KEY_OCT -> { menstrualPeriodMonthly.oct = data }
                                    Const.KEY_NOV -> { menstrualPeriodMonthly.nov = data }
                                    Const.KEY_DEC -> { menstrualPeriodMonthly.dec = data }
                                }
                            }
                        }
                        progressGetMenstrualRecord.postValue(false)
                        successGetMenstrualRecord.postValue(menstrualPeriodMonthly)
                    }
                    .addOnFailureListener {exception ->
                        progressGetMenstrualRecord.postValue(false)
                        errorGetMenstrualRecord.postValue(exception.message)
                    }
            }catch (e: Exception){
                progressGetMenstrualRecord.postValue(false)
                errorGetMenstrualRecord.postValue(e.message)
            }
        }
    }
}
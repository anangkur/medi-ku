package com.anangkur.mediku.feature.view.mens.menstrual

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodMonthly
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.util.Const
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MenstrualViewModel(private val repository: Repository): ViewModel() {

    var activeYear = ""

    var activeYearData: MenstrualPeriodMonthly? = null

    var maxCycleLong = 0
    var minCycleLong = 0
    var periodLong = 0
    var activeMonth = 0

    val progressGetMenstrualRecord = MutableLiveData<Boolean>()
    val successGetMenstrualRecord = MutableLiveData<MenstrualPeriodMonthly>()
    val errorGetMenstrualRecord = MutableLiveData<String>()
    fun getMenstrualPeriod(year: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressGetMenstrualRecord.postValue(true)
                val user = repository.remoteRepository.firebaseAuth.currentUser
                activeYear = year
                periodLong = 0
                activeMonth = 0
                repository.remoteRepository.firestore
                    .collection(Const.COLLECTION_MENSTRUAL_PERIOD)
                    .document(user?.uid?:"")
                    .collection(activeYear)
                    .get()
                    .addOnSuccessListener {result ->
                        val menstrualPeriodMonthly = MenstrualPeriodMonthly()
                        if (!result.isEmpty){
                            for (querySnapshot in result){

                                val data = querySnapshot?.toObject<MenstrualPeriodResume>()

                                if (data != null){
                                    if (maxCycleLong == 0 && minCycleLong == 0){
                                        maxCycleLong = data.longCycle
                                        minCycleLong = data.longCycle
                                    }
                                    if (data.longCycle > maxCycleLong){
                                        maxCycleLong = data.longCycle
                                    }
                                    if (data.longCycle < minCycleLong){
                                        minCycleLong = data.longCycle
                                    }
                                    periodLong += data.longPeriod
                                    activeMonth++
                                }

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
                            periodLong /= activeMonth
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

    val progressAddMenstrualRecord = MutableLiveData<Boolean>()
    val successAddMenstrualRecord = MutableLiveData<MenstrualPeriodResume>()
    val errorAddMenstrualRecord = MutableLiveData<String>()
    fun addMenstrualPeriod(menstrualPeriodResume: MenstrualPeriodResume, date: Date){
        val month = SimpleDateFormat("MMMM", Locale.US).format(date)
        addMenstrualResumeDataMonth(month, menstrualPeriodResume)
        CoroutineScope(Dispatchers.IO).launch {
            repository.addMenstrualPeriod(menstrualPeriodResume, object: BaseFirebaseListener<MenstrualPeriodResume>{
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

    fun getMenstrualResumeDataCurrentMonth(month: String): MenstrualPeriodResume? {
        return when (month){
            Const.KEY_JAN -> activeYearData?.jan
            Const.KEY_FEB -> activeYearData?.feb
            Const.KEY_MAR -> activeYearData?.mar
            Const.KEY_APR -> activeYearData?.apr
            Const.KEY_MAY -> activeYearData?.may
            Const.KEY_JUN -> activeYearData?.jun
            Const.KEY_JUL -> activeYearData?.jul
            Const.KEY_AUG -> activeYearData?.aug
            Const.KEY_SEP -> activeYearData?.sep
            Const.KEY_OCT -> activeYearData?.oct
            Const.KEY_NOV -> activeYearData?.nov
            Const.KEY_DEC -> activeYearData?.dec
            else -> null
        }
    }

    private fun addMenstrualResumeDataMonth(month: String, data: MenstrualPeriodResume) {
        when (month){
            Const.KEY_JAN -> activeYearData?.jan = data
            Const.KEY_FEB -> activeYearData?.feb = data
            Const.KEY_MAR -> activeYearData?.mar = data
            Const.KEY_APR -> activeYearData?.apr = data
            Const.KEY_MAY -> activeYearData?.may = data
            Const.KEY_JUN -> activeYearData?.jun = data
            Const.KEY_JUL -> activeYearData?.jul = data
            Const.KEY_AUG -> activeYearData?.aug = data
            Const.KEY_SEP -> activeYearData?.sep = data
            Const.KEY_OCT -> activeYearData?.oct = data
            Const.KEY_NOV -> activeYearData?.nov = data
            Const.KEY_DEC -> activeYearData?.dec = data
        }
    }
}
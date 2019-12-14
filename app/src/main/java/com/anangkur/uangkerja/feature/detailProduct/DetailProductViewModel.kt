package com.anangkur.uangkerja.feature.detailProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.data.Repository
import com.anangkur.uangkerja.data.model.BaseResponse
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.data.model.product.DetailProduct

class DetailProductViewModel(private val repository: Repository): ViewModel(){

    var productId: String = ""

    private val reloadTrigger = MutableLiveData<String>()
    val detailProductLiveData: LiveData<Result<BaseResponse<DetailProduct>>> = Transformations.switchMap(reloadTrigger){
        repository.getDetailProduct(it)
    }

    fun getDetailProduct(){
        reloadTrigger.value = productId
    }
}
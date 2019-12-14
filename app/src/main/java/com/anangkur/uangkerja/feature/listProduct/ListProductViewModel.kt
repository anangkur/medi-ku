package com.anangkur.uangkerja.feature.listProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.data.Repository
import com.anangkur.uangkerja.data.model.BasePagination
import com.anangkur.uangkerja.data.model.BaseResponse
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.data.model.product.Product

class ListProductViewModel(private val repository: Repository): ViewModel(){

    private val reloadTrigger = MutableLiveData<Boolean>()
    val listProductLiveData: LiveData<Result<BaseResponse<BasePagination<Product>>>> = Transformations.switchMap(reloadTrigger){
        repository.getListProduct()
    }

    fun getListProduct(){
        reloadTrigger.value = true
    }
}
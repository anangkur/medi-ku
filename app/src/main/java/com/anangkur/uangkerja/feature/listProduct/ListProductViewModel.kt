package com.anangkur.uangkerja.feature.listProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.data.Repository
import com.anangkur.uangkerja.data.model.BasePagination
import com.anangkur.uangkerja.data.model.BaseResponse
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.data.model.product.Category
import com.anangkur.uangkerja.data.model.product.Product
import com.anangkur.uangkerja.data.model.product.ProductParam

class ListProductViewModel(private val repository: Repository): ViewModel(){

    var category: Int? = null
    var categoryActive: String? = null

    private val reloadTrigger = MutableLiveData<ProductParam>()
    val listProductLiveData: LiveData<Result<BaseResponse<BasePagination<Product>>>> =
        Transformations.switchMap(reloadTrigger){
            repository.getListProduct(it?.category, it?.page)
        }
    fun getListProduct(page: Int?){
        reloadTrigger.value = ProductParam(page, category)
    }

    private val reloadTriggerCategory = MutableLiveData<Boolean>()
    val listCategoryLiveData: LiveData<Result<BaseResponse<List<Category>>>> =
        Transformations.switchMap(reloadTriggerCategory){
            repository.getListCategory()
        }
    fun getListCategory(){
        reloadTriggerCategory.value = true
    }
}
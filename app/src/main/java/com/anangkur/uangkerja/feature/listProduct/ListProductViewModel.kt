package com.anangkur.uangkerja.feature.listProduct

import androidx.lifecycle.*
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
    val tempListProduct = ArrayList<Product>()
    val listProductLiveData = MutableLiveData<List<Product>>()
    val loadMoreLive = MutableLiveData<Boolean>()
    var positionStart = 0
    var differentCount = 0
    var nextPage = 0
    var pagelast = 0
    val resultListProductLiveData: LiveData<Result<BaseResponse<BasePagination<Product>>>> =
        Transformations.switchMap(reloadTrigger){
            repository.getListProduct(it?.category, it?.page)
        }
    fun getListProduct(page: Int?){
        reloadTrigger.value = ProductParam(page, category)
    }
    fun paginateData(data: BasePagination<Product>){
        val latestData = ArrayList<Product>()
        with(tempListProduct){
            if (data.currentPage == 1){
                clear()
                nextPage = 1
                positionStart = 0
            } else {
                nextPage++
                positionStart = tempListProduct.size
            }
            addAll(data.data)
            latestData.clear()
            latestData.addAll(data.data)
        }
        pagelast = data.currentPage
        loadMoreLive.value = pagelast < data.lastPage
        differentCount = tempListProduct.size - positionStart
        listProductLiveData.value = latestData
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
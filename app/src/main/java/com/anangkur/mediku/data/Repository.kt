package com.anangkur.mediku.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.anangkur.mediku.data.local.LocalRepository
import com.anangkur.mediku.data.model.BasePagination
import com.anangkur.mediku.data.model.BaseResponse
import com.anangkur.mediku.data.remote.RemoteRepository
import kotlinx.coroutines.Dispatchers
import com.anangkur.mediku.data.model.Result
import com.anangkur.mediku.data.model.auth.Register
import com.anangkur.mediku.data.model.auth.ResponseLogin
import com.anangkur.mediku.data.model.product.Category
import com.anangkur.mediku.data.model.product.DetailProduct
import com.anangkur.mediku.data.model.product.Product
import com.anangkur.mediku.data.model.profile.ResponseUser
import kotlinx.coroutines.withContext

class Repository(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) {

    fun saveApiToken(apiToken: String){
        localRepository.saveApiToken(apiToken)
    }

    fun loadApiToken(): String?{
        return localRepository.loadApiToken()
    }

    fun deleteApiToken(){
        return localRepository.deleteApiToken()
    }

    fun postLogin(email: String, password: String): LiveData<Result<ResponseLogin>> =
        liveData(Dispatchers.IO){
            emit(Result.loading())
            val response = remoteRepository.postLogin(email, password)
            val responseLive = MutableLiveData<Result<ResponseLogin>>()
            if (response.status == Result.Status.SUCCESS) {
                withContext(Dispatchers.Main){
                    responseLive.value = response
                    emitSource(responseLive)
                }
            } else if (response.status == Result.Status.ERROR) {
                withContext(Dispatchers.Main){
                    emit(Result.error(response.message?:""))
                    emitSource(responseLive)
                }
            }
        }

    fun postRegister(name: String, email: String, password: String, passwordConfirm: String): LiveData<Result<BaseResponse<Register>>> =
        liveData(Dispatchers.IO){
            emit(Result.loading())
            val response = remoteRepository.postSignup(name, email, password, passwordConfirm)
            val responseLive = MutableLiveData<Result<BaseResponse<Register>>>()
            if (response.status == Result.Status.SUCCESS) {
                withContext(Dispatchers.Main){
                    responseLive.value = response
                    emitSource(responseLive)
                }
            } else if (response.status == Result.Status.ERROR) {
                withContext(Dispatchers.Main){
                    emit(Result.error(response.message?:""))
                    emitSource(responseLive)
                }
            }
        }

    fun getProfile(): LiveData<Result<ResponseUser>> =
        liveData(Dispatchers.IO){
            emit(Result.loading())
            val response = remoteRepository.getProfile("Bearer ${loadApiToken()}")
            val responseLive = MutableLiveData<Result<ResponseUser>>()
            if (response.status == Result.Status.SUCCESS) {
                withContext(Dispatchers.Main){
                    responseLive.value = response
                    emitSource(responseLive)
                }
            } else if (response.status == Result.Status.ERROR) {
                withContext(Dispatchers.Main){
                    emit(Result.error(response.message?:""))
                    emitSource(responseLive)
                }
            }
        }

    fun getListProduct(category: Int?, page: Int?): LiveData<Result<BaseResponse<BasePagination<Product>>>> =
        liveData {
            emit(Result.loading())
            val response =
                remoteRepository.getListProduct("Bearer ${loadApiToken()}", category, page)
            val responseLive = MutableLiveData<Result<BaseResponse<BasePagination<Product>>>>()
            if (response.status == Result.Status.SUCCESS){
                withContext(Dispatchers.Main){
                    responseLive.value = response
                    emitSource(responseLive)
                }
            }else if (response.status == Result.Status.ERROR){
                withContext(Dispatchers.Main){
                    emit(Result.error(response.message?:""))
                    emitSource(responseLive)
                }
            }
        }

    fun getListCategory(): LiveData<Result<BaseResponse<List<Category>>>> =
        liveData {
            emit(Result.loading())
            val response = remoteRepository.getListCategory("Bearer ${loadApiToken()}")
            val responseLive = MutableLiveData<Result<BaseResponse<List<Category>>>>()
            if (response.status == Result.Status.SUCCESS){
                withContext(Dispatchers.Main){
                    responseLive.value = response
                    emitSource(responseLive)
                }
            }else if (response.status == Result.Status.ERROR){
                withContext(Dispatchers.Main){
                    emit(Result.error(response.message?:""))
                    emitSource(responseLive)
                }
            }
        }

    fun getDetailProduct(productId: String): LiveData<Result<BaseResponse<DetailProduct>>> =
        liveData {
            emit(Result.loading())
            val response = remoteRepository.getDetailProduct("Bearer ${loadApiToken()}", productId)
            val responseLive = MutableLiveData<Result<BaseResponse<DetailProduct>>>()
            if (response.status == Result.Status.SUCCESS){
                withContext(Dispatchers.Main){
                    responseLive.value = response
                    emitSource(responseLive)
                }
            }else if (response.status == Result.Status.ERROR){
                withContext(Dispatchers.Main){
                    emit(Result.error(response.message?:""))
                    emitSource(responseLive)
                }
            }
        }

    companion object{
        @Volatile private var INSTANCE: Repository? = null
        fun getInstance(remoteRepository: RemoteRepository, localRepository: LocalRepository) = INSTANCE ?: synchronized(
            Repository::class.java){
            INSTANCE ?: Repository(remoteRepository, localRepository).also { INSTANCE = it }
        }
    }
}
package com.nakeeljr.olalekanlawalapp.viewmodel

import androidx.lifecycle.ViewModel
import com.nakeeljr.olalekanlawalapp.model.DiscoveryResponse
import com.nakeeljr.olalekanlawalapp.model.state.Resource
import com.nakeeljr.olalekanlawalapp.rest.ApiError
import com.nakeeljr.olalekanlawalapp.rest.ApiService
import com.nakeeljr.olalekanlawalapp.utils.SingleLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DiscoveryViewModel  constructor(
    private val apiService: ApiService
): ViewModel() {


    private val _discoveryDataResult = SingleLiveData<Resource<List<DiscoveryResponse>>>()
    val discoveryDataResult: SingleLiveData<Resource<List<DiscoveryResponse>>> = _discoveryDataResult


    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    //get discovery data
    fun getDiscoveryData() {
        _discoveryDataResult.value = Resource.loading()
        val disposable  = apiService.getDiscoveryData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _discoveryDataResult.value = Resource.success(it!!)
                },
                {
                    val error = ApiError(it).message
                    _discoveryDataResult.value = Resource.error(error)
                })
        disposables.add(disposable)
    }

    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}



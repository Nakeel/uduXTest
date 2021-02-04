package com.nakeeljr.olalekanlawalapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nakeeljr.olalekanlawalapp.rest.ApiService

/**
 * ViewModel provider factory to instantiate DiscoveryViewModel.
 * Required given DiscoveryViewModel has a non-empty constructor
 */
class DiscoveryViewModelFactory(val apiService: ApiService) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoveryViewModel::class.java)) {
            return DiscoveryViewModel(
                apiService = apiService

            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

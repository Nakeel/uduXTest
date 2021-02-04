package com.nakeeljr.olalekanlawalapp.rest

import com.nakeeljr.olalekanlawalapp.model.DiscoveryResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {


    @GET("/api/v1/screens/discover")
    fun getDiscoveryData() : Observable<List<DiscoveryResponse>>

}
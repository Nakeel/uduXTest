package com.nakeeljr.olalekanlawalapp.rest

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import com.nakeeljr.olalekanlawalapp.BuildConfig


object RetrofitApiClient {


    //create a logging interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60,TimeUnit.SECONDS)
        .build()
    

    //make an instance of the class which returns a apiService type
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }
}
//BuildConfig.API_URL
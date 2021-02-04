package com.nakeeljr.olalekanlawalapp.rest

import com.google.gson.GsonBuilder
import com.nakeeljr.olalekanlawalapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    val service: ApiService = createService()

    private fun createService(): ApiService  {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(LoggingInterceptor())
        //httpClientBuilder.addInterceptor(ServiceInterceptor())


        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        builder.client(httpClientBuilder.build())
        return builder.build().create(ApiService::class.java)
    }


    class ServiceInterceptor : Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            if(request.header("No-Authentication")==null){
                //get token
                val token = ""
                //or use Token Function
                if(!token.isNullOrEmpty()) {
                    val finalToken =  "Bearer "+ token
                    request = request.newBuilder()
                        .addHeader("Authorization",finalToken)
                        .build()
                }
            }
            return chain.proceed(request)
        }
    }
}


package com.nakeeljr.olalekanlawalapp.rest

import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {
    private val TAG = LoggingInterceptor::class.java.name

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val apiKey = "a24ca990-ea11-4be1-80ef-b3687369234b"
        val newRequest = originalRequest.newBuilder()
            .addHeader("user-agent", "vscode-restclient")
            .addHeader("content-type", "application/json")
            .addHeader("x-api-key", apiKey)
            .addHeader("cache-control","no-cache")
            .build()

        val t1 = System.nanoTime()
        Log.d(
            TAG, String.format(
                "Retrofit Sending request %s on %s%n%sn%s",
                newRequest.url(), chain.connection(), newRequest.headers(), newRequest.body()
            )
        )
        val response = chain.proceed(newRequest)
        val t2 = System.nanoTime()
        Log.d(TAG, String.format("%s", response.networkResponse()))
        Log.d(
            TAG, String.format(
                "Retrofit Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()
            )
        )
        return response
    }
}
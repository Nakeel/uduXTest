package com.nakeeljr.olalekanlawalapp.rest

import android.content.Context
import android.content.Intent
import com.google.gson.JsonParser
import retrofit2.HttpException

class ApiError constructor(error: Throwable) {
    var message = "An error occurred"
    init {
        if (error is HttpException) {
            val errorJsonString = error.response()?.errorBody()?.string()
            try {
                this.message = JsonParser().parse(errorJsonString)
                    .asJsonObject["message"]
                    .asString
            }catch (e:Exception){
               this.message ="couldn't complete operation,Please try again"
            }

        }
        else {
            this.message = error.message ?: this.message
        }
    }
}
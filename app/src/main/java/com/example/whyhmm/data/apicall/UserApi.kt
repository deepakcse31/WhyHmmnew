package com.example.whyhmm.data.apicall

import com.example.whyhmm.domain.data.loginresponse
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {
    @POST("loginAuthapi/getOtp")
    suspend fun login(@Header("Authorization") token: String, @Body user: JsonObject?): loginresponse

}
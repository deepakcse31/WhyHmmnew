package com.example.whyhmm.domain.repository

import com.example.whyhmm.data.apicall.SafeApiCall
import com.example.whyhmm.data.apicall.UserApi
import com.example.whyhmm.domain.utils.Constants
import com.google.gson.JsonObject
import javax.inject.Inject

class loginRepository @Inject constructor(private var userApi: UserApi) : SafeApiCall {
    suspend fun getUser(num: JsonObject) = safeApiCall {
        userApi.login(Constants.INTIAL_TOKEN , num)
    }
}
package com.example.turkishmuseums.source.api

import com.example.turkishmuseums.utilities.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor : Interceptor {
    override fun intercept (chain: Interceptor. Chain): Response {

        val request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("apiKey", API_KEY).build()
        val newRequest = request.newBuilder().url(url).build()

        /*
        val authorizedRequest = request.newBuilder().header( "Authorization", "Bearer ${API_KEY}").build()
        return chain. proceed (authorizedRequest)
        */

        return chain.proceed(newRequest)
    }
}
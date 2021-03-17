package com.movies.android.util

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder().addQueryParameter(API_KEY_PARAMETER, API_KEY).build()
        val newRequest = request.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val API_KEY_PARAMETER = "api_key"
        const val API_KEY = "1cc33b07c9aa5466f88834f7042c9258" // this should be at least hidden with NDK help
    }
}
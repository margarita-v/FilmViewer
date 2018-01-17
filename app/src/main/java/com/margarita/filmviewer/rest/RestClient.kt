package com.margarita.filmviewer.rest

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestClient {

    private var retrofit: Retrofit

    companion object {
        private const val API_KEY_NAME = "api_key"
        private const val API_KEY_VALUE = "6ccd72a2a8fc239b13f209408fc31c33"

        private const val SORT_NAME = "sort_by"
        private const val SORT_VALUE = "popularity.desc"

        private const val BASE_URL = "https://developers.themoviedb.org/3/"

        private const val IMAGE_SIZE = "w342/"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/" + IMAGE_SIZE
    }

    init {
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor())
                .build()

        retrofit = Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
    }

    /**
     * Function for creation API-service for a concrete class type
     */
    fun <T> createService(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    /**
     * Interceptor implementation for OkHttpClient
     * in order to use API key for getting access to the site content
     */
    class ApiKeyInterceptor: Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            // Get original request
            val originalRequest = chain.request()

            // Add API key as a query parameter
            val url = originalRequest.url().newBuilder()
                    .addQueryParameter(API_KEY_NAME, API_KEY_VALUE)
                    .addQueryParameter(SORT_NAME, SORT_VALUE)
                    .build()

            // Build request with a new url
            val request = originalRequest.newBuilder()
                    .url(url)
                    .build()
            return chain.proceed(request)
        }
    }
}
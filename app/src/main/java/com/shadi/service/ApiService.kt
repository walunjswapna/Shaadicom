package com.shadi.service


import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shadi.local_db.entity.CandidateListResponse
import com.shadi.network.ConnectivityInterceptor
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {


    @GET("?results=10")
    fun getCandidateList(): Deferred<CandidateListResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiService {
            val requestInterceptor = Interceptor { chain ->

                val request = chain.request()
                    .newBuilder()
                    ?.addHeader("grant-type", "temp-device-token")
                    ?.addHeader("Content-Type", "application/json")
                    ?.addHeader("Accept", "application/json").build()
                return@Interceptor chain.proceed(request)
            }


            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient: OkHttpClient =
                OkHttpClient.Builder()
                        .readTimeout(120, TimeUnit.SECONDS)
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .addInterceptor(requestInterceptor)
                        .addInterceptor(logging)
                        .addInterceptor(connectivityInterceptor)
                        .build()


            val gson = GsonBuilder()
                .setLenient()// lenient can help with some structural issues of JSON 
                .create()
            return Retrofit.Builder().client(okHttpClient)
                .baseUrl("https://randomuser.me/api/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) // this is useful when calling retrofit using Coroutine
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)

        }


    }
}

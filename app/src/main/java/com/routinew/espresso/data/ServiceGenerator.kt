package com.routinew.espresso.data

import com.auth0.android.result.Credentials
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.routinew.espresso.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ServiceGenerator {
    companion object {
        private val moshi = MoshiConverterFactory.create (
            Moshi.Builder().run {
                addLast(KotlinJsonAdapterFactory())
                build()
            }
        )
        private val httpClientBuilder = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(StethoInterceptor())
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
            }
        }

        fun retrofitBuilder(server: String, apiPath: String) = Retrofit.Builder().apply {
                addConverterFactory(moshi)
                baseUrl("http://$server/$apiPath")
        }


        fun <S> createService(serviceClass : Class<S>, server: String, apiPath: String, interceptor: Interceptor? =null) : S {
            if (interceptor != null && interceptor !in httpClientBuilder.interceptors()) {
                httpClientBuilder.addInterceptor(interceptor)
            }

            val retrofit = retrofitBuilder(server,apiPath)
                .client(httpClientBuilder.build())
                .build()
            return retrofit.create(serviceClass)
        }
    }

}
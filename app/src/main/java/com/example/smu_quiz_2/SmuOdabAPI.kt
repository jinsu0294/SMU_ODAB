package com.example.smu_quiz_2

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SmuOdabAPI {

    fun smuQuizInfoRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://test.com/")
            .build()
        return retrofit!!
    }

}
package com.smu_odab.smu_quiz_2

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SmuOdabAPI {

    fun smuInfoRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://ec2-54-180-99-213.ap-northeast-2.compute.amazonaws.com:8000")
            .build()
        return retrofit!!
    }

}
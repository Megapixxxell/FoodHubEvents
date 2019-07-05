package com.example.foodhubevents.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EventServiceApi {

    fun getEventServiceApi(): EventsService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.8.152:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(EventsService::class.java)
    }

}
package com.example.foodhubevents.koin

import android.content.Context
import com.example.foodhubevents.api.EventServiceApi
import com.example.foodhubevents.data.EventDataSource
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val EVENT_SP = "EVENT_SP"

val appModule = module {

//    factory { Gson() }
//    single { androidContext().getSharedPreferences(EVENT_SP, Context.MODE_PRIVATE) }
//    single { EventDataSource(get(), get()) }

    single { EventServiceApi().getEventServiceApi() }

}
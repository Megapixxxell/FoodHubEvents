package com.example.foodhubevents.api

import com.example.foodhubevents.data.Event
import com.example.foodhubevents.data.PostEvent
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface EventsService {

    @GET("events/comingsoon")
    fun getEvent(): Single<List<Event>>

    @GET("events/all")
    fun getAllEvent(): Single<List<Event>>

    @GET("events/{id}")
    fun getEventFromId(@Path("id") id: Long ): Single<Event>

    @GET("events/date/{date}")
    fun getEventFromDay(@Path("date") date: String ): Single<List<Event>>

    @POST("events/regEvent")
    fun postEvent(@Body event: PostEvent): Completable





}
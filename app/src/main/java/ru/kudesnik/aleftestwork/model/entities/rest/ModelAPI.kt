package ru.kudesnik.aleftestwork.model.entities.rest

import retrofit2.Call
import retrofit2.http.GET

interface ModelAPI {
    @GET("list.php")
    fun getModel(): Call<Array<String>>
}
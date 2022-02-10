package ru.kudesnik.aleftestwork.model.entities.rest

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {
    const val baseUrl = "http://dev-tasks.alef.im/task-m-001/"

    fun getOkHTTPBuilder(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        return httpClient.build()
    }
}
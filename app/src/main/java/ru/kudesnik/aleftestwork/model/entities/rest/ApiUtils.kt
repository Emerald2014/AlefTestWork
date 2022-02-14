package ru.kudesnik.aleftestwork.model.entities.rest

import android.app.Application
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import ru.kudesnik.aleftestwork.MainActivity
import java.util.concurrent.TimeUnit

object ApiUtils {
    const val baseUrl = "http://dev-tasks.alef.im/task-m-001/"

    fun getOkHTTPBuilder(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)


//        httpClient.cache(CoilUtils.createDefaultCache(MainActivity.this))
        return httpClient.build()
    }
}

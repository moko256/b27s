package com.github.moko256.b27s

import android.app.Activity
import android.app.Application
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class GlobalApp : Application() {
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()

        okHttpClient = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(1, 300, TimeUnit.MILLISECONDS))
            .followRedirects(false)
            .followSslRedirects(false)
            .build()
    }

}

fun Activity.getApp() = application as GlobalApp
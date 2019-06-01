package com.github.moko256.b27s.shortlink

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Bundle
import com.github.moko256.b27s.getApp
import kotlinx.coroutines.*
import okhttp3.Request

class ShortLinkOpenActivity : Activity(), CoroutineScope {
    override val coroutineContext = Job() + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent?.data?.toString()?.replaceFirst("http:", "https:")
        if (url != null) {
            launch {
                startActivity(

                    withContext(Dispatchers.Default) {
                        try {
                            getApp()
                                .okHttpClient
                                .newCall(
                                    Request.Builder()
                                        .url(url)
                                        .get()
                                        .build()
                                )
                                .execute()
                                .header("Location")
                                ?.let { Intent(Intent.ACTION_VIEW, Uri.parse(it)) }
                                ?: Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(FLAG_ACTIVITY_NEW_TASK).excludeOwnApp(this@ShortLinkOpenActivity)
                        } catch (e: Throwable) {
                            Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(FLAG_ACTIVITY_NEW_TASK).excludeOwnApp(this@ShortLinkOpenActivity)
                        }
                    }
                        .apply {
                            intent.extras?.also {
                                putExtras(it)
                            }
                        }
                )
            }
        }
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}

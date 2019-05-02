package com.github.moko256.b27s.shortlink

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

fun Intent.excludeOwnApp(context: Context): Intent = run {
    val packageManager = context.packageManager
    val intents = packageManager
        .queryIntentActivities(
            this,
            PackageManager.MATCH_ALL
        )
        .also {
            if (it.size == 1) {
                it.addAll(
                    packageManager
                        .queryIntentActivities(
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://")),
                            PackageManager.MATCH_ALL
                        )
                )
            }
        }
        .asSequence()
        .map { it.activityInfo.packageName }
        .distinct()
        .filter {
            it != context.packageName
        }.map {
            Intent(this).setPackage(it)
        }.toMutableList()

    when {
        intents.isEmpty() -> {
            Intent.createChooser(Intent(), "")
        }
        intents.size == 1 -> {
            intents[0]
        }
        else -> {
            Intent.createChooser(
                Intent(),
                ""
            ).putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray())
        }
    }
}
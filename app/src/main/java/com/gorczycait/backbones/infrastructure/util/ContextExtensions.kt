package com.gorczycait.backbones.infrastructure.util

import android.content.Context
import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
import android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import java.io.File

fun Context.openAppSystemSettings() {
    val intent = Intent().apply {
        action = ACTION_APPLICATION_DETAILS_SETTINGS
        data = "package:$packageName".toUri()
        addCategory(CATEGORY_DEFAULT)
        addFlags(FLAG_ACTIVITY_NEW_TASK)
        addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }
    startActivity(intent)
}

fun Context.openVideoPlayer(filePath: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(
            FileProvider.getUriForFile(
                this@openVideoPlayer,
                "$packageName.provider",
                File(filePath),
            ),
            "video/*",
        )
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    startActivity(intent)
}

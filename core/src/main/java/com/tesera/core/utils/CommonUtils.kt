package com.tesera.core.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.text.format.DateUtils
import android.webkit.MimeTypeMap
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun Date.niceDateStr() = DateUtils.getRelativeTimeSpanString(
    this.time,
    Calendar.getInstance().timeInMillis,
    DateUtils.MINUTE_IN_MILLIS
).toString()

fun Uri.getFileExtension(context: Context): String? {
    return if (this.scheme.equals(ContentResolver.SCHEME_CONTENT)) {
        val contentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val result = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(this))
        result
            ?: tryOrNull {
                MimeTypeMap.getFileExtensionFromUrl(
                    Uri.fromFile(File(this.path!!)).toString()
                )
            }
    } else {
        MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(this.path!!)).toString())
    }
}


inline fun <reified T> tryOrNull(expression: () -> T?): T? =
    try {
        expression()
    } catch (e: Throwable) {
        null
    }

fun String.toDate(): Date {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return parser.parse(this) ?: Date()
}
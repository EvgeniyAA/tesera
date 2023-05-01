package com.tesera.core.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


fun openInBrowser(
    url: String,
    context: Context,
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

fun openFile(
    filePath: String,
    context: Context,
) {
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    val file = File(filePath)
    val uri = FileProvider.getUriForFile(
        context, context.packageName + ".fileprovider", file
    )
    intent.data = uri
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(Intent.createChooser(intent, ""))
}

fun share(
    share: String,
    url: String,
    context: Context,
) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_SUBJECT, share)
    intent.putExtra(Intent.EXTRA_TEXT, url)
    context.startActivity(Intent.createChooser(intent, share))
}
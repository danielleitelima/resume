package com.danielleitelima.resume.foundation.presentation.foundation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

const val CONTENT_TYPE_PDF = "application/pdf"
const val CONTENT_TYPE_HEADER = "Content-Type"

fun Context.mailTo(email: String, subject: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    startActivity(intent)
}

fun Context.launchUrl(url: String, themeColor: Int? = null) {
    CoroutineScope(Dispatchers.IO).launch {
        val request = Request.Builder().url(url).head().build()

        try {
            val response = OkHttpClient().newCall(request).execute()
            val contentType = response.header(CONTENT_TYPE_HEADER)
            if (contentType == CONTENT_TYPE_PDF) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(Uri.parse(url), CONTENT_TYPE_PDF)
                }
                startActivity(intent)
            } else {
                val intent = CustomTabsIntent.Builder()
                    .setDefaultColorSchemeParams(
                        CustomTabColorSchemeParams.Builder()
                            .also {
                               if (themeColor != null) it.setToolbarColor(themeColor)
                            }
                            .build()
                    )
                    .setColorSchemeParams(
                        CustomTabsIntent.COLOR_SCHEME_DARK,
                        CustomTabColorSchemeParams.Builder()
                            .also {
                                if (themeColor != null) it.setToolbarColor(themeColor)
                            }
                            .build()
                    )
                    .build()
                intent.launchUrl(this@launchUrl, Uri.parse(url))
            }
        } catch (e: IOException) { }
    }
}


fun Context?.copyToClipboard(text: String, message: String = "") {
    this ?: return
    val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clipData = ClipData.newPlainText("text", text)
    clipboardManager?.setPrimaryClip(clipData)

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        Toast
            .makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }
}
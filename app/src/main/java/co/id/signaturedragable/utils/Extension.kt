package co.id.signaturedragable.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.annotation.LayoutRes


fun Context.showToast(text: String) {
    Toast.makeText(this, "$text", Toast.LENGTH_SHORT).show()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun String.guessFileName(): String {
    return URLUtil.guessFileName(
        this,
        null,
        null
    )
}
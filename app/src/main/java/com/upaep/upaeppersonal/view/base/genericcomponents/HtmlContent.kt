package com.upaep.upaeppersonal.view.base.genericcomponents

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun HtmlContent(content: String, fontSize: Float, withImage: Boolean = false) {
    if(withImage) {
        val styledContent = if (withImage) {
            "<style>img { max-width: 100%; height: auto; }</style>$content"
        } else {
            content
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadDataWithBaseURL(null, styledContent, "text/html", "utf-8", null)
                }
            }
        )
    } else {
        AndroidView(
            factory = { context -> TextView(context) },
            update = {
                it.text = HtmlCompat.fromHtml(
                    content,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
                it.textSize = fontSize
            }
        )
    }
}
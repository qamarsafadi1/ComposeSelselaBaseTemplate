package com.qamar.composetemplate.ui.core.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.qamar.composetemplate.util.bindHtml
import com.selsela.cpapp.databinding.HtmlTextBinding

@Composable
fun HtmlText(htmlText: String) {
    AndroidViewBinding(HtmlTextBinding::inflate) {

        textHtml.text = bindHtml(htmlText)
    }
}
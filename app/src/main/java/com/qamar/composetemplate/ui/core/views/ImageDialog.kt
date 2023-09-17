package com.qamar.composetemplate.ui.core.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import com.qamar.composetemplate.data.remote.categories.model.products.Product
import com.qamar.composetemplate.ui.core.component.ZoomableImage
import com.qamar.composetemplate.util.getResource
import com.selsela.cpapp.R


@Composable
 fun ImageDialog(
    openDialog: MutableState<Boolean>,
    selectedImage: String,
    product: Product
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {

            },
            text = {
                ZoomableImage(
                    selectedImage, if (product.images.isEmpty().not())
                        product.images else listOf(
                        com.qamar.composetemplate.data.remote.categories.model.products.Image(
                            imageUrl = product.imageUrl.ifEmpty { product.image },
                        )
                    )
                )            },
            confirmButton = {
            },
            dismissButton = {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = R.drawable.close.getResource(),
                        contentDescription = "Close",
                        modifier = Modifier.clickable {
                            openDialog.value = false
                        }
                    )
                }
            },
            backgroundColor = Color.Transparent,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true
            ),
            modifier = Modifier.fillMaxSize()

        )
    }
}
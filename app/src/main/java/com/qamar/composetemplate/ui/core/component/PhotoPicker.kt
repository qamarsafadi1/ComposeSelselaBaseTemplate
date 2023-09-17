package com.qamar.composetemplate.ui.core.component

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.qamar.composetemplate.data.remote.auth.model.user.User
import com.qamar.composetemplate.ui.theme.ColorSecondary


@Composable
fun PhotoPickerChooser(
    modifier: Modifier,
    isMultiple: Boolean = false,
    onImageSelected: (Uri) -> Unit
) {
    var selectedImageUri by remember {
        mutableStateOf(User.currentUser?.avatar)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract =
        if (isMultiple.not())
            ActivityResultContracts.PickVisualMedia()
        else ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                if (uri is Uri) {
                    selectedImageUri = uri.toString()
                    onImageSelected(uri)
                }
            }
        }
    )
    val galleryLauncher =
        rememberLauncherForActivityResult(
            if (isMultiple.not())
                ActivityResultContracts.GetContent()
            else ActivityResultContracts.GetMultipleContents()
        ) { uri ->
            if (uri != null) {
                if (uri is Uri) {
                    selectedImageUri = uri.toString()
                    onImageSelected(uri)
                }
            }
        }
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        AsyncImage(
            imageUrl = selectedImageUri ?: "",
            modifier = Modifier
                .clip(CircleShape)
                .size(140.dp),
            contentScale = ContentScale.Crop
        )

        FloatingActionButton(
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                } else {
                    galleryLauncher.launch("image/*")
                }
            }, shape = CircleShape,
            modifier = Modifier
                .padding(bottom = 5.dp, end = 18.dp)
                .align(Alignment.BottomStart)
                .size(33.dp),
            containerColor = ColorSecondary
        ) {

            Icon(
                Icons.Default.Edit, contentDescription = "",
                tint = Color.White
            )

        }
    }
}
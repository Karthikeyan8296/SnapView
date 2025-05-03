package com.example.snapview.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.snapview.ui.theme.InterFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadBottomSheet(
    modifier: Modifier = Modifier,
    onDismissReq: () -> Unit,
    isOpen: Boolean,
    sheetState: SheetState,
    onOptionClick: (ImageDownloadOption) -> Unit,
    option: List<ImageDownloadOption> = ImageDownloadOption.entries
) {
    if (isOpen) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = onDismissReq,
            sheetState = sheetState
        ) {
            option.forEach { option ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOptionClick(option) }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option.label,
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

enum class ImageDownloadOption(val label: String) {
    SMALL(label = "Download Small Size"),
    MEDIUM(label = "Download Medium Size"),
    ORIGINAL(label = "Download Original Size")
}

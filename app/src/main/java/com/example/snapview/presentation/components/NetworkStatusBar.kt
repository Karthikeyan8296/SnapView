package com.example.snapview.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snapview.ui.theme.InterFontFamily

@Composable
fun NetworkStatusBar(
    modifier: Modifier = Modifier,
    showMessageBar: Boolean,
    backgroundColor: Color,
    message: String
) {
    AnimatedVisibility(
        visible = showMessageBar,
        enter = fadeIn() + slideInVertically(animationSpec = tween(durationMillis = 600) { height -> height }),
        exit = fadeOut() + slideOutVertically(animationSpec = tween(durationMillis = 600) { height -> height })
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = backgroundColor)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                fontSize = 16.sp,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
    }


}
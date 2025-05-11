package com.example.snapview.presentation.screens.OnBoardingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snapview.R
import com.example.snapview.presentation.components.ImageCarousel
import com.example.snapview.ui.theme.InterFontFamily

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onButtonClick: () -> Unit
) {
    val images = listOf(
        R.drawable.pic1,
        R.drawable.pic2,
        R.drawable.pic3,
        R.drawable.pic4,
        R.drawable.pic5,
        R.drawable.pic6,
        R.drawable.pic7
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_removed),
                contentDescription = "",
                modifier = Modifier
                    .size(42.dp)
            )
            Text(
                text = "SnapView",
                fontSize = 24.sp,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(80.dp))

        ImageCarousel(imageList = images)

        Spacer(modifier = Modifier.height(65.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Transform Your Screen, One\nWallpaper at a Time!",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "Stunning wallpapers at transform your screen.\nFresh designs, endless inspiration",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = { onButtonClick() })
                    .background(color = Color(0xFF0D3A75)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = "Get Started \uD83D\uDE80", fontSize = 18.sp,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOnBoarding() {
//    OnBoardingScreen(paddingValues = PaddingValues())
}
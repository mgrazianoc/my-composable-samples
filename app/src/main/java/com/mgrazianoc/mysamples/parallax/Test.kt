package com.mgrazianoc.mysamples.parallax

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mgrazianoc.mysamples.R
import com.mgrazianoc.mysamples.ui.theme.MyComposeSamplesTheme

@Composable
fun Test(){

    var value by remember { mutableStateOf(0f) }

    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .background(color = Color(0XFF141414))
    ) {
        Slider(
            value = value,
            onValueChange = {value = it},
            valueRange = -1f..1f,
            modifier = Modifier.fillMaxWidth(0.8f).align(Alignment.TopCenter),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.DarkGray
            )
        )

        // image assets configurations
        val imagePixelHeight = 1034
        val imagePixelWidth = 685

        // cards configurations
        val cardWidthProportion = 0.6f
        val cardWidth = maxWidth * cardWidthProportion
        val cardHeight = cardWidth * imagePixelHeight / imagePixelWidth

        // image composable configuration for parallax effect
        val parallaxProportion = 1.5f
        val imageHeight = cardHeight * parallaxProportion
        val imageHeightDiff = imageHeight - cardHeight
        val imageHeightDelta = imageHeightDiff / 2
        val diff = imageHeightDelta * value

        Box(modifier = Modifier.size(cardWidth, cardHeight).background(Color.White).align(Alignment.Center)){
            Image(
                painter = painterResource(id = R.drawable.beauty_1),
                contentDescription = "beauty",
                contentScale = ContentScale.Crop,
                modifier = Modifier.alpha(0.5f).graphicsLayer {
                    scaleX = parallaxProportion
                    scaleY = parallaxProportion
                    translationY = diff.value
                }
            )
        }
    }

}


@Preview
@Composable
fun Test_Preview(){
    MyComposeSamplesTheme {
        Test()
    }
}
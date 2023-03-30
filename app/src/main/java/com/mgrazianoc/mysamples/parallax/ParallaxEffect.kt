package com.mgrazianoc.mysamples.parallax

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mgrazianoc.mysamples.R
import com.mgrazianoc.mysamples.parallax.models.CardState
import com.mgrazianoc.mysamples.parallax.models.transitionToCenter
import com.mgrazianoc.mysamples.parallax.models.normalizedItemPosition
import com.mgrazianoc.mysamples.ui.theme.MyComposeSamplesTheme


sealed class ParallaxEffect{
    data class Classic(
        val fillMaxWidth: Float,
        val imageRatio: Float,
        val scaleFactor: Float
    ): ParallaxEffect()

    data class Inverted(
        val fillMaxWidth: Float,
        val imageRatio: Float,
        val velocityFactor: Float
    ): ParallaxEffect()
}


@Composable
fun ParallaxLazyColumn(
    cardStateList: List<CardState>,
    parallaxEffect: ParallaxEffect,
    horizontalAlignment: Alignment.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier,
){
    when(parallaxEffect){
        is ParallaxEffect.Classic -> {
            ParallaxClassicLazyColumn(
                cardStateList = cardStateList,
                parallaxEffect = parallaxEffect,
                horizontalAlignment = horizontalAlignment,
                verticalArrangement = verticalArrangement,
                modifier = modifier
            )
        }
        is ParallaxEffect.Inverted -> {
            ParallaxInvertedLazyColumn(
                cardStateList = cardStateList,
                parallaxEffect = parallaxEffect,
                horizontalAlignment = horizontalAlignment,
                verticalArrangement = verticalArrangement,
                modifier = modifier
            )
        }
    }
}


@Composable
private fun ParallaxClassicLazyColumn(
    cardStateList: List<CardState>,
    parallaxEffect: ParallaxEffect.Classic,
    horizontalAlignment: Alignment.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier
){
    BoxWithConstraints(modifier = Modifier
        .fillMaxWidth()
        .then(modifier)) {
        val cardWidth = maxWidth * parallaxEffect.fillMaxWidth
        val cardHeight = cardWidth * 1 / parallaxEffect.imageRatio

        val scaledImageHeight = cardHeight * parallaxEffect.scaleFactor
        val scaledImageHeightDiff = scaledImageHeight - cardHeight

        val verticalPadding = (maxHeight - cardHeight) * 0.5f

        val lazyColumnState = rememberLazyListState()
        LazyColumn(
            state = lazyColumnState,
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement,
            contentPadding = PaddingValues(vertical = verticalPadding),
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
        ){
            items(cardStateList, key = { it.id } ){ item ->
                Image(
                    painter = painterResource(id = item.resourceId),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(cardWidth, cardHeight)
                        .alpha(0.99f)
                        .graphicsLayer {
                            scaleX = parallaxEffect.scaleFactor
                            scaleY = parallaxEffect.scaleFactor

                            val norm = lazyColumnState.layoutInfo.normalizedItemPosition(item.id)
                            translationY = -norm * scaledImageHeightDiff.value / 2f
                        }
                )
            }
        }
    }
}


@Composable
private fun ParallaxInvertedLazyColumn(
    cardStateList: List<CardState>,
    parallaxEffect: ParallaxEffect.Inverted,
    horizontalAlignment: Alignment.Horizontal,
    verticalArrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier
){
    BoxWithConstraints(modifier = Modifier.fillMaxSize().then(modifier)) {
        val cardWidth = maxWidth * parallaxEffect.fillMaxWidth
        val cardHeight = cardWidth * 1 / parallaxEffect.imageRatio

        val verticalPadding = (maxHeight - cardHeight) * 0.5f

        val lazyColumnState = rememberLazyListState()
        LazyColumn(
            state = lazyColumnState,
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement,
            contentPadding = PaddingValues(vertical = verticalPadding),
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
        ){
            items(cardStateList, key = { it.id } ){ item ->
                Image(
                    painter = painterResource(id = item.resourceId),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(cardWidth, cardHeight)
                        .alpha(0.99f)
                        .graphicsLayer {
                            val t = lazyColumnState.layoutInfo.transitionToCenter(item.id)
                            translationY = -t * parallaxEffect.velocityFactor
                        }
                )
            }
        }
    }
}


@Composable
@Preview
private fun ParallaxLazyColumnClassic_Preview(){
    val assets = listOf(
        R.drawable.beauty_1,
        R.drawable.beauty_2,
        R.drawable.beauty_3,
        R.drawable.beauty_4,
        R.drawable.beauty_5,
    )

    val cardStateList = (0 until 5).map { CardState(it, assets[it % assets.size], "beauty") }
    val parallaxEffect = ParallaxEffect.Classic(
        fillMaxWidth = 0.8f,
        imageRatio = 8 / 11f,
        scaleFactor = 2f
    )

    MyComposeSamplesTheme {
        ParallaxLazyColumn(
            cardStateList = cardStateList,
            parallaxEffect = parallaxEffect,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        )
    }
}


@Composable
@Preview
private fun ParallaxLazyColumnInverted_Preview(){
    val assets = listOf(
        R.drawable.beauty_1,
        R.drawable.beauty_2,
        R.drawable.beauty_3,
        R.drawable.beauty_4,
        R.drawable.beauty_5,
    )

    val cardStateList = (0 until 5).map { CardState(it, assets[it % assets.size], "beauty") }
    val parallaxEffect = ParallaxEffect.Inverted(
        fillMaxWidth = 0.8f,
        imageRatio = 8 / 11f,
        velocityFactor = 1.01f
    )

    MyComposeSamplesTheme {
        ParallaxInvertedLazyColumn(
            cardStateList = cardStateList,
            parallaxEffect = parallaxEffect,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        )
    }
}
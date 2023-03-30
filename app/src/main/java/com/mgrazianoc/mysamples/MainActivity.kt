package com.mgrazianoc.mysamples

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.mgrazianoc.mysamples.parallax.ParallaxEffect
import com.mgrazianoc.mysamples.parallax.ParallaxLazyColumn
import com.mgrazianoc.mysamples.parallax.models.CardState
import com.mgrazianoc.mysamples.ui.theme.MyComposeSamplesTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            hideSystemUI()
        }
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
            imageRatio = 7 / 13f,
            velocityFactor = 1.05f
        )

        setContent {
            MyComposeSamplesTheme {
                Box(){
                    Image(
                        painter = painterResource(id = R.drawable.background2),
                        contentDescription = "background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    ParallaxLazyColumn(
                        cardStateList = cardStateList,
                        parallaxEffect = parallaxEffect,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun hideSystemUI() {

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        // Configure the behavior of the hidden system bars.
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Add a listener to update the behavior of the toggle fullscreen button when
        // the system bars are hidden or revealed.
        window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
            // You can hide the caption bar even when the other system bars are visible.
            // To account for this, explicitly check the visibility of navigationBars()
            // and statusBars() rather than checking the visibility of systemBars().
            if (windowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
                || windowInsets.isVisible(WindowInsetsCompat.Type.statusBars())) {
                // Hide both the status bar and the navigation bar.
                windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

            }
//            else {
//                windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
//            }
            view.onApplyWindowInsets(windowInsets)
        }



    }
}

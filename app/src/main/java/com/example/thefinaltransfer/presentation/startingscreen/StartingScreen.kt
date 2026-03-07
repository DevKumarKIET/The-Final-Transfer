package com.example.thefinaltransfer.presentation.startingscreen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navigation.Routes

sealed class StartingScreens(
    val title: String,
    val description: String,
    val icon: Int
) {
    data object First : StartingScreens(
        title = "Safeguard Your Digital Legacy",
        description = "Protect your most precious digital files inside a secure, encrypted vault that only you control.",
        icon = R.drawable.lock
    )

    data object Second : StartingScreens(
        title = "Transfer to Trusted Nominees",
        description = "Your encrypted packets travel securely to your chosen nominees when the time comes.",
        icon = R.drawable.share
    )

    data object Third : StartingScreens(
        title = "Peaceful Automated Transfer",
        description = "Automatic release based on inactivity or death verification. Your legacy, protected forever.",
        icon = R.drawable.favorite_icon
    )
}

@Composable
fun StartingScreen(navHostController: NavHostController) {

    val pages = remember {
        listOf(
            StartingScreens.First,
            StartingScreens.Second,
            StartingScreens.Third
        )
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )

    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pages.size - 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.BackgroundCream))
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
        ) { position ->
            OnboardingPageContent(page = pages[position])
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            repeat(pages.size) { index ->

                val selected = pagerState.currentPage == index

                val width by animateDpAsState(
                    targetValue = if (selected) 28.dp else 10.dp,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = ""
                )

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(8.dp)
                        .width(width)
                        .clip(CircleShape)
                        .background(
                            if (selected)
                                colorResource(id = R.color.PrimaryOrange)
                            else
                                colorResource(id = R.color.TextBody)
                        )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF9F45),
                            Color(0xFFFFB703)
                        )
                    )
                )
                .clickable {
                    if (!isLastPage) {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1
                            )
                        }
                    } else {
                        // Navigate to LoginScreen when on the last page
                        navHostController.navigate(Routes.LoginScreen)
                    }
                },
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = if (isLastPage) "Get Started" else "Next",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!isLastPage) {
            Text(
                text = "Skip",
                color = colorResource(id = R.color.skip_text),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { navHostController.navigate(Routes.LoginScreen) }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun OnboardingPageContent(page: StartingScreens) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF9F45),
                            Color(0xFFFFB703)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painterResource(id = page.icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(72.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = page.title,
            color = colorResource(id = R.color.black),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            color = colorResource(id = R.color.black),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
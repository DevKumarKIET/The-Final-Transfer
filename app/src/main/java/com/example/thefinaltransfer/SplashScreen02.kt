package com.example.thefinaltransfer

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ... inside your OnboardingScreen2 Composable
val context = LocalContext.current


// --- COLORS (Same as previous screen) ---
val CreamBackground = Color(0xFFFDF8F2)
val BrandOrange = Color(0xFFE66D35)
val TextBrown = Color(0xFF4A342E)
val TextGrey = Color(0xFF7A6C68)
val DotInactive = Color(0xFFE5DCD5)

@Composable
fun OnboardingScreen2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // --- 1. THE SHARE ICON CONTAINER ---
        Box(
            modifier = Modifier
                .size(140.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFB74D), BrandOrange)
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // Using the standard Share icon
            Image(
                painter = painterResource(id = R.drawable.share),
                contentDescription = "Share Icon",
                modifier = Modifier.size(70.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // --- 2. UPDATED TEXT CONTENT ---
        Text(
            text = "Share with Trusted People",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = TextBrown,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Grant controlled access to family members and trusted individuals for documents they need.",
            fontSize = 16.sp,
            color = TextGrey,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // --- 3. PAGE INDICATOR DOTS (Middle is Active) ---


        Spacer(modifier = Modifier.weight(1f))

        // --- 4. BUTTONS ---
        Button(
            onClick = {
                // Create an Intent to launch MainActivity
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = BrandOrange,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Next",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            // Create an Intent to launch MainActivity
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(
                text = "Skip",
                color = TextBrown, // Text color matches title slightly
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun PreviewOnboarding2() {
    OnboardingScreen2()
}
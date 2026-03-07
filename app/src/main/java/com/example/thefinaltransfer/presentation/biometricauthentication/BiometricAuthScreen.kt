package com.example.thefinaltransfer.presentation.biometricauthentication

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navigation.Routes

@Composable
fun BiometricSetupScreen(navHostController: NavHostController,
    isBiometricAvailable: Boolean = true // Pass false to test "No Hardware" state
) {
    // Background Gradient (Consistent with previous screens)
    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.pastel_bg_top),
            colorResource(id = R.color.pastel_bg_mid),
            colorResource(id = R.color.pastel_bg_bot)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- 1. Top Navigation ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back Button
                Row(
                    modifier = Modifier.clickable { navHostController.navigate(Routes.SignUpOTPScreen) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.gradient_start),
                        modifier = Modifier.size(24.dp)
                            .clickable { navHostController.navigate(Routes.LoginScreen) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Back",
                        color = colorResource(id = R.color.gradient_start),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable { navHostController.navigate(Routes.LoginScreen) }
                    )
                }

                // Step Indicator
                Surface(
                    color = colorResource(id = R.color.brand_orange).copy(alpha = 0.1f),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Step 4 of 4",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.brand_orange),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // --- 2. Hero Section with Pulse Animation ---
            // This represents the "Unique" element request
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(200.dp)
            ) {
                // Animated Pulse Background
                PulseAnimation(color = colorResource(id = R.color.brand_orange))

                // Central Icon Container
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 10.dp,
                    modifier = Modifier.size(120.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Fingerprint,
                            contentDescription = "Biometric",
                            tint = colorResource(id = R.color.brand_orange),
                            modifier = Modifier.size(66.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            // --- 3. Text Content ---
            Text(
                text = "Secure Your Vault",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Enable biometric access for faster login and bank-grade security for your digital legacy.",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.Gray,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- 4. Benefits List ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                BiometricBenefitItem(text = "Fast & Secure Login")
                Spacer(modifier = Modifier.height(12.dp))
                BiometricBenefitItem(text = "Private & Unique to You")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- 5. Action Buttons ---

            // Only show Enable button if hardware is available
            if (isBiometricAvailable) {
                GradientButton(
                    text = "Enable Biometrics",
                    onClick = {navHostController.navigate(Routes.HomeScreen) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                // Optional: Show message if hardware missing
                Text(
                    text = "Biometric hardware not detected on this device.",
                    color = Color.Red.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Skip Button (Always available)
            TextButton(onClick = {navHostController.navigate(Routes.HomeScreen)}) {
                Text(
                    text = "Skip for Now",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// --- Custom Pulse Animation Composable ---
@Composable
fun PulseAnimation(color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")

    // Animate 3 circles with different delays
    val scale1 by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 1.6f,
        animationSpec = infiniteRepeatable(tween(1500), RepeatMode.Restart), label = "s1"
    )
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.5f, targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(1500), RepeatMode.Restart), label = "a1"
    )

    val scale2 by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 1.6f,
        animationSpec = infiniteRepeatable(tween(1500, delayMillis = 500), RepeatMode.Restart), label = "s2"
    )
    val alpha2 by infiniteTransition.animateFloat(
        initialValue = 0.5f, targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(1500, delayMillis = 500), RepeatMode.Restart), label = "a2"
    )

    Box(contentAlignment = Alignment.Center) {
        // Outer Ripple 1
        Box(
            modifier = Modifier
                .size(110.dp)
                .graphicsLayer {
                    scaleX = scale1
                    scaleY = scale1
                    alpha = alpha1
                }
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f))
        )
        // Outer Ripple 2
        Box(
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer {
                    scaleX = scale2
                    scaleY = scale2
                    alpha = alpha2
                }
                .clip(CircleShape)
                .background(color.copy(alpha = 0.2f))
        )
    }
}

@Composable
fun BiometricBenefitItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = null,
            tint = Color(0xFF4CAF50), // Green for success/security
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333)
        )
    }
}

// Reusing your Gradient Button from previous steps
@Composable
private fun GradientButton(
    text: String,
    onClick: () -> Unit
) {
    val brush = Brush.horizontalGradient(
        colors = listOf(
            colorResource(id = R.color.gradient_start),
            colorResource(id = R.color.gradient_end)
        )
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = colorResource(id = R.color.gradient_start)),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

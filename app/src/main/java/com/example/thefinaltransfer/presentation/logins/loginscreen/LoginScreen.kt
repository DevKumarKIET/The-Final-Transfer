package com.example.thefinaltransfer.presentation.logins.loginscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.thefinaltransfer.R
import com.example.thefinaltransfer.presentation.navigation.Routes

@Composable
fun LoginMethodScreen(navHostController: NavHostController) {
    // UPDATED: The new pastel background colors you provided
    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFF7ED), // Top
            Color(0xFFFFFBEB), // Middle
            Color(0xFFFFEDD4)  // Bottom
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush) // Apply the new pastel gradient
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- 1. Top Shield Icon ---
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(24.dp))
                // Using a very subtle orange tint for the icon background to blend with the new page color
                .background(Color(0xFFFFF7ED))
                .border(1.dp, colorResource(id = R.color.brand_orange).copy(alpha = 0.2f), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                // Ensure you have R.drawable.lock or use Icons.Outlined.Security
                painter = painterResource(id = R.drawable.lock),
                contentDescription = "Security",
                tint = colorResource(id = R.color.brand_orange),
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- 2. Title Texts ---
        Text(
            text = "Welcome Back",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black.copy(alpha = 0.85f)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Choose your login method",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray,
                letterSpacing = 0.5.sp
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        // --- 3. Login Option Cards ---

        // Option A: Email Login
        LoginOptionCard(
            title = "Email Login",
            subtitle = "Login with your registered\nemail",
            icon = Icons.Outlined.Email,
            // Keep the Icon Gradient vibrant (Orange -> Yellow) as per the card design
            iconGradient = Brush.linearGradient(
                colors = listOf(
                    colorResource(id = R.color.gradient_start), // FF9F45
                    colorResource(id = R.color.gradient_end)    // FFB703
                )
            ),
            onClick = {navHostController.navigate(Routes.EmailLoginScreen)}
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Option B: Mobile Login
        LoginOptionCard(
            title = "Mobile Login",
            subtitle = "Login with your mobile number",
            icon = Icons.Outlined.Phone,
            // Keep the Icon Gradient vibrant (Yellow -> Peach) as per the card design
            iconGradient = Brush.linearGradient(
                colors = listOf(
                    colorResource(id = R.color.phone_grad_start), // FFB703
                    colorResource(id = R.color.phone_grad_end)    // FFD6B0
                )
            ),
            onClick = {navHostController.navigate(Routes.MobileLoginScreen)}
        )

        Spacer(modifier = Modifier.height(60.dp))

        // --- 4. Footer ---
        val footerText = buildAnnotatedString {
            append("Don't have an account? ")
            // Assuming 'skip_text' or 'brand_orange' is your orange color
            withStyle(style = SpanStyle(color = colorResource(id = R.color.brand_orange), fontWeight = FontWeight.Bold)) {
                append("Create Account")
            }
        }

        Text(
            text = footerText,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.clickable { navHostController.navigate(Routes.SignUpScreen) }
        )
    }
}

@Composable
fun LoginOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconGradient: Brush,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Lower elevation for a softer look
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(1.dp, Color(0xFFFFF7ED), RoundedCornerShape(16.dp)) // Subtle border matching bg
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gradient Icon Box
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp)) // Slightly more rounded
                    .background(iconGradient),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text Column
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.85f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }

            // Arrow Icon
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "Go",
                tint = Color(0xFFFFB703), // Light orange/yellow tint for the arrow
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginMethod() {
//    LoginMethodScreen({}, {}, {})
//}
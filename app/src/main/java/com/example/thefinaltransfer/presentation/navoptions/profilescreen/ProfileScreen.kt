package com.example.thefinaltransfer.presentation.navoptions.profilescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// --- Centralized Theme Colors ---
private object ProfileColors {
    val BgCream = Color(0xFFFFF6EE)
    val TextBlack = Color(0xFF1A1A1A)
    val TextGray = Color(0xFF757575)

    // Gradients
    val HeaderGradient = Brush.linearGradient(listOf(Color(0xFFEA580C), Color(0xFFF97316), Color(0xFFFB923C)))
    val UserCardGradient = Brush.linearGradient(listOf(Color(0xFFFF9F45), Color(0xFFFF7A00)))
    val AvatarGradient = Brush.linearGradient(listOf(Color(0xFFFF9F45),Color(0xFFFFB703)))
    val StatusCardGradient = Brush.linearGradient(listOf(Color(0xFFFFB2B2), Color(0xFFFF7F7F)))
    val IconGradient = Brush.linearGradient(listOf(Color(0xFFFF9F45), Color(0xFFFFB703)))

    val CardBorder = Color(0xFFF5E6D8)
    val ErrorRed = Color(0xFFD32F2F)
}

@Composable
fun ProfileScreen(navHostController: NavHostController?) {

    Scaffold(
        containerColor = ProfileColors.BgCream,
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(ProfileColors.BgCream)
                .padding(innerPadding), // Ensures content doesn't hide under nav bar
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            // --- 1. HEADER & USER PROFILE CARD ---
            item {
                HeaderAndUserCard()
            }

            // --- 2. CURRENT STATUS SECTION ---
            item {
                Spacer(modifier = Modifier.height(24.dp))
                CurrentStatusCard()
            }

            // --- 3. ACCOUNT SETTINGS ---
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionTitle("Account Settings")
                Spacer(modifier = Modifier.height(12.dp))

                SettingsItemCard(
                    icon = Icons.Outlined.Security,
                    title = "Security & Privacy",
                    subtitle = "Password, biometrics, encryption"
                )
                SettingsItemCard(
                    icon = Icons.Outlined.Notifications,
                    title = "Notifications",
                    subtitle = "Manage alerts and reminders"
                )
                SettingsItemCard(
                    icon = Icons.Outlined.Settings,
                    title = "App Preferences",
                    subtitle = "Language, theme, display"
                )
            }

            // --- 4. HELP & SUPPORT ---
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionTitle("Help & Support")
                Spacer(modifier = Modifier.height(12.dp))

                SettingsItemCard(
                    icon = Icons.Outlined.HelpOutline,
                    title = "Help Center",
                    subtitle = "FAQs and guides"
                )
                SettingsItemCard(
                    icon = Icons.Outlined.ErrorOutline,
                    title = "Contact Support",
                    subtitle = "Get help from our team"
                )
                SettingsItemCard(
                    icon = Icons.Outlined.Article,
                    title = "Legal & Policies",
                    subtitle = "Terms, privacy, compliance"
                )
            }

            // --- 5. FOOTER & LOGOUT ---
            item {
                Spacer(modifier = Modifier.height(32.dp))
                AppFooter()
                Spacer(modifier = Modifier.height(24.dp))
                LogoutButton(onLogoutClick = { /* Handle Logout */ })
            }
        }
    }
}

// --- Modual UI Components ---

@Composable
private fun HeaderAndUserCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        // Curved Orange Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(ProfileColors.HeaderGradient)
                .padding(horizontal = 18.dp, vertical = 40.dp)
        ) {

            Text(
                text = "Profile",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )

            // Overlapping User Card
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .shadow(12.dp, RoundedCornerShape(20.dp), spotColor = Color(0x33000000)),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .background(ProfileColors.UserCardGradient)
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar Box
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = Color(0x33000000))
                            .background(ProfileColors.AvatarGradient, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center,

                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = "Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // User Info
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Justice",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "justice@email.com",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }

                    // Edit Button
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .clickable { /* Edit Profile */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit Profile",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrentStatusCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .shadow(6.dp, RoundedCornerShape(20.dp), spotColor = Color(0x1A000000)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(ProfileColors.StatusCardGradient)
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Current Status",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Box 1: Check-in Status (White bg with 40% Opacity)
            StatusIndicatorBox(
                icon = Icons.Outlined.CheckCircle,
                title = "Check-in Status",
                subtitle = "Last verified 2 days ago",
                badgeText = "Active"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Box 2: Packet Security (White bg with 40% Opacity)
            StatusIndicatorBox(
                icon = Icons.Outlined.Lock,
                title = "Packet Security",
                subtitle = "All packets encrypted",
                badgeText = "Secured"
            )
        }
    }
}

@Composable
private fun StatusIndicatorBox(
    icon: ImageVector,
    title: String,
    subtitle: String,
    badgeText: String
) {
    // 40% opacity white background requested in prompt
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                .background(ProfileColors.IconGradient, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = ProfileColors.TextBlack
            )
            Text(text = subtitle, fontSize = 12.sp, color = ProfileColors.TextGray)
        }

        // White Pill Badge
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(50))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = badgeText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileColors.TextBlack
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = ProfileColors.TextBlack,
        modifier = Modifier.padding(horizontal = 24.dp)
    )
}

@Composable
private fun SettingsItemCard(icon: ImageVector, title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .clickable { /* Navigate */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, ProfileColors.CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFFFF0E0), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFFFF9F45),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ProfileColors.TextBlack
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 12.sp, color = ProfileColors.TextGray)
            }

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFFFFD6B0)
            )
        }
    }
}

@Composable
private fun AppFooter() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(1.dp, ProfileColors.CardBorder, RoundedCornerShape(12.dp))
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "The Final Transfer v1.0.0",
                    fontSize = 12.sp,
                    color = ProfileColors.TextGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "© 2026 All rights reserved",
                    fontSize = 12.sp,
                    color = ProfileColors.TextGray
                )
            }
        }
    }
}

@Composable
private fun LogoutButton(onLogoutClick: () -> Unit) {
    Box(modifier = Modifier.padding(horizontal = 24.dp)) {
        OutlinedButton(
            onClick = onLogoutClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFFFCDCD)),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Logout,
                    contentDescription = "Logout",
                    tint = ProfileColors.ErrorRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Logout",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ProfileColors.ErrorRed
                )
            }
        }
    }
}


